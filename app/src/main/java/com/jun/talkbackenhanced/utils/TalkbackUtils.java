package com.jun.talkbackenhanced.utils;

public class TalkbackUtils {

    public static int TYPE_VIEW_CLICKED = 1;

    public static int TYPE_WINDOW_CONTENT_CHANGED = 2048;

    public static int TYPE_WINDOWS_CHANGED = 4194304;

    public static int TYPE_WINDOW_STATE_CHANGED = 32;

    public static int TYPE_VIEW_ACCESSIBILITY_FOCUSED = 32768;

    public static int TYPE_VIEW_ACCESSIBILITY_FOCUS_CLEARED = 65536;

    public static int TYPE_TOUCH_INTERACTION_END = 2097152;

    public static int TYPE_TOUCH_INTERACTION_START = 1048576;

    public static int TYPE_VIEW_SELECTED = 4;

    public static int TYPE_VIEW_SCROLLED = 4096;

    public static int TYPE_VIEW_LONG_CLICKED = 2;

    public static int TYPE_VIEW_HOVER_ENTER = 128;

    public static int TYPE_VIEW_HOVER_EXIT = 256;

    public static int TYPE_ANNOUNCEMENT = 16384;

    public static int CONTENT_CHANGE_TYPE_PANE_TITLE = 8;

    public static int CONTENT_CHANGE_TYPE_PANE_APPEARED = 16;

    public static int TYPE_VIEW_TEXT_SELECTION_CHANGED = 8192;

    public static String eventTypeToString(int accessibilityEvent) {
        switch (accessibilityEvent) {
            case 1 :
                return "TYPE_VIEW_CLICKED";
            case 2048 :
                return "TYPE_WINDOW_CONTENT_CHANGED";
            case 4194304:
                return "TYPE_WINDOWS_CHANGED";
            case 32 :
                return "TYPE_WINDOW_STATE_CHANGED";
            case 32768:
                return "TYPE_VIEW_ACCESSIBILITY_FOCUSED";
            case 65536:
                return "TYPE_VIEW_ACCESSIBILITY_FOCUS_CLEARED";
            case 2097152:
                return "TYPE_TOUCH_INTERACTION_END";
            case 1048576:
                return "TYPE_TOUCH_INTERACTION_START";
            case 4:
                return "TYPE_VIEW_SELECTED";
            case 4096:
                return "TYPE_VIEW_SCROLLED";
            case 2:
                return "TYPE_VIEW_LONG_CLICKED";
            case 128:
                return "TYPE_VIEW_HOVER_ENTER";
            case 256:
                return "TYPE_VIEW_HOVER_EXIT";
            case 16384:
                return "TYPE_ANNOUNCEMENT";
            case 8:
                return "CONTENT_CHANGE_TYPE_PANE_TITLE";
            case 16:
                return "CONTENT_CHANGE_TYPE_PANE_APPEARED";
            case 8192:
                return "TYPE_VIEW_TEXT_SELECTION_CHANGED";
            default:
                return "OTHER_ACCESSIBILITY_EVENT / EVENT INTEGER VALUE : " + accessibilityEvent;
        }
    }

}
