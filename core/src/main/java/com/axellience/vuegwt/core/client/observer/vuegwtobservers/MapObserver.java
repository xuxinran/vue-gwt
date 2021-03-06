package com.axellience.vuegwt.core.client.observer.vuegwtobservers;

import static com.axellience.vuegwt.core.client.tools.VueGWTTools.wrapMethod;

import com.axellience.vuegwt.core.client.observer.VueGWTObserver;
import com.axellience.vuegwt.core.client.observer.VueGWTObserverManager;
import com.axellience.vuegwt.core.client.observer.VueObserver;
import com.axellience.vuegwt.core.client.tools.AfterMethodCall;
import com.axellience.vuegwt.core.client.tools.JsUtils;
import java.util.Map;

/**
 * This observer is able to observe Java Collections. For now it only support List and Set.
 * <br>
 * To observe the collection, it wraps the Java mutable methods and call Vue observer when they are
 * called.
 *
 * @author Adrien Baron
 */
public class MapObserver extends VueGWTObserver {

  @Override
  public boolean observe(Object object) {
    if (object instanceof Map) {
      observeMap((Map) object);
      return true;
    }

    return false;
  }

  private void observeMap(Map map) {
    VueObserver observer = VueGWTObserverManager.get().getVueObserver(map);
    observer.observeArray(JsUtils.arrayFrom(map));

    AfterMethodCall<Map> callObserver =
        ((object, methodName, result, arguments) -> observer.notifyDep());

    wrapMethod(map, "clear", callObserver);
    wrapMethod(map, "remove", callObserver);

    wrapMethod(map, "put", ((object, methodName, result, args) -> {
      observer.notifyDep();
      observer.observeArray(new Object[]{args[1]});
    }));
    wrapMethod(map, "putIfAbsent", ((object, methodName, result, args) -> {
      observer.notifyDep();
      observer.observeArray(new Object[]{args[1]});
    }));
    wrapMethod(map, "putAll", ((object, methodName, result, args) -> {
      observer.notifyDep();
      observer.observeArray(JsUtils.arrayFrom(((Map<?, ?>) args[0])));
    }));

    wrapMethod(map, "replace", ((object, methodName, result, args) -> {
      observer.notifyDep();
      observer.observeArray(new Object[]{args[1]});
    }));
  }
}
