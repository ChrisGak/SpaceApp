package com.spaceApplication.client.space.html;

import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.ui.IntegerBox;

/**
 * Created by Кристина on 16.05.2016.
 */
public class NumbersOnlyKeyPressHandler implements KeyPressHandler {
        @Override
        public void onKeyPress(KeyPressEvent event) {

            switch (event.getNativeEvent().getKeyCode()) {
                case KeyCodes.KEY_TAB:
                case KeyCodes.KEY_BACKSPACE:
                case KeyCodes.KEY_DELETE:
                case KeyCodes.KEY_LEFT:
                case KeyCodes.KEY_RIGHT:
                case KeyCodes.KEY_UP:
                case KeyCodes.KEY_DOWN:
                case KeyCodes.KEY_END:
                case KeyCodes.KEY_ENTER:
                case KeyCodes.KEY_ESCAPE:
                case KeyCodes.KEY_PAGEDOWN:
                case KeyCodes.KEY_PAGEUP:
                case KeyCodes.KEY_HOME:
                case KeyCodes.KEY_SHIFT:
                case KeyCodes.KEY_ALT:
                case KeyCodes.KEY_CTRL:
                    break;
                default:

                    if (event.isAltKeyDown() || (event.isControlKeyDown() && (event.getCharCode() != 'v' && event.getCharCode() != 'V')))
                        break;

                    if (!Character.isDigit(event.getCharCode()))
                        if (event.getSource() instanceof IntegerBox)
                            ((IntegerBox) event.getSource()).cancelKey();
            }

        }
}