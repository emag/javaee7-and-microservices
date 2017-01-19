package microservices.ribbon_hystrix_consul.events;

import com.netflix.hystrix.HystrixInvokableInfo;
import com.netflix.ribbon.hystrix.FallbackHandler;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.UnpooledByteBufAllocator;
import rx.Observable;

import java.util.Map;

public class TimeFallbackHandler implements FallbackHandler<ByteBuf> {

  @Override
  public Observable<ByteBuf> getFallback(HystrixInvokableInfo<?> hystrixInfo, Map<String, Object> requestProps) {
    String fallback = "{ \"h\": 12 }";
    byte[] bytes = fallback.getBytes();
    ByteBuf byteBuf = UnpooledByteBufAllocator.DEFAULT.buffer(bytes.length);
    byteBuf.writeBytes(bytes);
    return Observable.just(byteBuf);
  }

}
