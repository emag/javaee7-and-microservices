package zipkin;

import com.github.kristofa.brave.Brave;
import com.github.kristofa.brave.http.DefaultSpanNameProvider;
import com.github.kristofa.brave.http.SpanNameProvider;
import com.github.kristofa.brave.jaxrs2.BraveClientRequestFilter;
import com.github.kristofa.brave.jaxrs2.BraveClientResponseFilter;
import com.github.kristofa.brave.jaxrs2.BraveContainerRequestFilter;
import com.github.kristofa.brave.jaxrs2.BraveContainerResponseFilter;

import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;

import static com.github.kristofa.brave.internal.Util.checkNotNull;

public final class BraveTracingFeature implements Feature {

  static BraveTracingFeature create(Brave brave) {
    return new Builder(brave).build();
  }

  public static final class Builder {
    final Brave brave;
    SpanNameProvider spanNameProvider = new DefaultSpanNameProvider();

    Builder(Brave brave) { // intentionally hidden
      this.brave = checkNotNull(brave, "brave");
    }

    BraveTracingFeature build() {
      return new BraveTracingFeature(this);
    }
  }

  final Brave brave;
  final SpanNameProvider spanNameProvider;

  BraveTracingFeature(Builder b) { // intentionally hidden
    this.brave = b.brave;
    this.spanNameProvider = b.spanNameProvider;
  }

  @Override
  public boolean configure(FeatureContext context) {
    BraveClientRequestFilter braveClientRequestFilter =
      new BraveClientRequestFilter(spanNameProvider, brave.clientRequestInterceptor());
    context.register(braveClientRequestFilter);

    BraveClientResponseFilter braveClientResponseFilter =
      new BraveClientResponseFilter(brave.clientResponseInterceptor());
    context.register(braveClientResponseFilter);

    BraveContainerRequestFilter braveContainerRequestFilter =
      new BraveContainerRequestFilter(brave.serverRequestInterceptor(), spanNameProvider);
    context.register(braveContainerRequestFilter);

    BraveContainerResponseFilter braveContainerResponseFilter =
      new BraveContainerResponseFilter(brave.serverResponseInterceptor());
    context.register(braveContainerResponseFilter);

    return true;
  }

}