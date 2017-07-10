package com.axellience.vuegwtexamples.client.examples.vonwithdomevent;

import com.axellience.vuegwt.client.VueComponent;
import com.axellience.vuegwt.jsr69.component.annotations.Component;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.user.client.Window;
import jsinterop.annotations.JsType;

/**
 * @author Adrien Baron
 */
@JsType
@Component
public class VOnWithDOMEventComponent extends VueComponent
{
    @Override
    public void created() {}

    public void warn(String message, NativeEvent event)
    {
        if (event != null)
        {
            event.preventDefault();
            message += " -> Event Type: " + event.getType();
        }

        Window.alert(message);
    }
}