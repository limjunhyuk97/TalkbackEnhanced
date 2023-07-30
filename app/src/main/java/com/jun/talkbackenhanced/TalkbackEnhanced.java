package com.jun.talkbackenhanced;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Intent;
import android.util.Log;
import android.util.SparseArray;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityWindowInfo;

import com.jun.talkbackenhanced.utils.TalkbackUtils;
import com.jun.talkbackenhanced.utils.TextToSpeechImpl;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Locale;
import java.util.Set;

public class TalkbackEnhanced extends AccessibilityService {

    private String TAG = "TalkbackEnhanced";

    private AccessibilityServiceInfo Info = new AccessibilityServiceInfo();

    private TextToSpeechImpl tts;

    // 접근성 서비스에 대한 설정 정의 : 접근성 이벤트 유형 지정, 구성 및 서비스에 대한 추가 정보 제공
    // 접근성 서비스 시작 전 메서드 호출
    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();

        tts = new TextToSpeechImpl(this, Locale.KOREA);

        Info.packageNames = new String[] {"com.google.android.apps.maps"};

        Info.eventTypes = AccessibilityEvent.TYPES_ALL_MASK;

        Info.notificationTimeout = 10;

        Info.feedbackType = AccessibilityServiceInfo.FEEDBACK_ALL_MASK;

        setServiceInfo(Info);
    }

    // 일단 Text 던, ContentDescription 이던 뭐든 가져와서 읽어주는 방향으로..!
    // 텍스트를 뽑아 오는 방식은 추후에 보완해야겠지만 일단 텍스트를 가져왔다는 한에서
    // - 제대로 읽어주기 위해서 어떤 기준으로 동작하는 알고리즘이 필요한지?
    // - 이를 어떻게 구현할 지?

    @Override
    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {

        if(accessibilityEvent.getEventType() == AccessibilityEvent.TYPE_VIEW_CLICKED) {

            AccessibilityNodeInfo targetNode= accessibilityEvent.getSource();

            Log.d(TAG, targetNode != null ? targetNode.toString() : "null");
            Log.d(TAG, targetNode.getParent().toString());

            if(accessibilityEvent.getContentDescription() != null ) {
                tts.speech(accessibilityEvent.getContentDescription().toString());
            }
            else {
                tts.speech(accessibilityEvent.getText().toString());
            }
        }



    }

    @Override
    public void onInterrupt() {

    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }
}