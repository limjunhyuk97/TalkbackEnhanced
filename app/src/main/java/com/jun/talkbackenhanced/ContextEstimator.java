package com.jun.talkbackenhanced;

import android.content.Context;
import android.graphics.Rect;
import android.util.Log;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ContextEstimator {

    String TAG = "ContextEstimator";

    AccessibilityNodeInfo rootNode;

    Rect focusedNodeRect;

    PriorityQueue<DistanceNode> priorityQueueForDistancing = new PriorityQueue<>();

    //** Static
    private static float estimateDistance(Rect rect1, Rect rect2) {
        float x1 = rect1.exactCenterX();
        float y1 = rect1.exactCenterY();
        float x2 = rect2.exactCenterX();
        float y2 = rect2.exactCenterY();
        return (float)Math.sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2));
    }

    //** Nested class
    private static class DistanceNode implements Comparable<DistanceNode> {
        float distance;
        CharSequence content;

        public DistanceNode(float distance, CharSequence content) {
            this.distance = distance;
            this.content = content;
        }

        @Override
        public int compareTo(DistanceNode distanceNode) {
            return Math.round(this.distance - distanceNode.distance);
        }
    }

    //** Constructor
    public ContextEstimator(AccessibilityNodeInfo rootNode, AccessibilityNodeInfo focusedNode) {
        this.rootNode = rootNode;
        this.focusedNodeRect = new Rect();
        focusedNode.getBoundsInWindow(this.focusedNodeRect);
    }

    //** Method
    public void traverseNodeTree() {
        Queue<AccessibilityNodeInfo> queue = new LinkedList<>();
        queue.add(rootNode);

        while(!queue.isEmpty()) {
            AccessibilityNodeInfo cur = queue.peek();
            queue.remove();

            // if cur is null, continue
            if(cur == null) continue;

            // Bringing Content or Text for Estimation
            if(cur.getText() != null || cur.getContentDescription() != null) {
                Rect curNodeRect = new Rect();
                cur.getBoundsInWindow(curNodeRect);

                DistanceNode distanceNode;
                float estimatedDistance = estimateDistance(this.focusedNodeRect, curNodeRect);

                // Bringing Text for Estimation : Priority ContentDescription > text
                if(cur.getContentDescription() != null) distanceNode = new DistanceNode(estimatedDistance, cur.getContentDescription());
                else distanceNode = new DistanceNode(estimatedDistance, cur.getText());

                this.priorityQueueForDistancing.add(distanceNode);
            }

            // Traversing children
            int childNodeCount = cur.getChildCount();
            for(int i=0; i<childNodeCount; ++i) {
                queue.add(cur.getChild(i));
            }
        }
    }

    public void printAllNodes() {
        Log.d(TAG, "\n");
        while(!this.priorityQueueForDistancing.isEmpty()) {
            DistanceNode node = this.priorityQueueForDistancing.peek();
            this.priorityQueueForDistancing.remove();

            Log.d(TAG, node.distance + " " + (node.content != null ? node.content.toString() : "null"));
        }
    }

    public String getEstimatedContext() {

        // Traverse AccessibilityNodeInfo Tree and get text / description data in order
        traverseNodeTree();

        String[] contexts = new String[]{"Number_Dash", "Separate_Dash", "Number_To", "Number_Minus"};

        int defaultIdx = 0;

        String[] patternMinus = new String[]{"=", ">", "<", "%", "T°", "C°"};
        String[] patternTo = new String[]{"[0-9]{1,2}:[0-9]{1,2}"};
        String[] patternDash = new String[]{"address", "bus", "subway", "train",
                "(([가-힣A-Za-z·\\d~\\-\\.]{2,}(로|길).[\\d]+)|([가-힣A-Za-z·\\d~\\-\\.]+(읍|동)\\s)[\\d]+)",
                "([0-9]+)-([0-9]+)", "버스", "도보", "지하철", "기차"};

        Log.d(TAG, "\n");

        while(!this.priorityQueueForDistancing.isEmpty()) {
            DistanceNode node = this.priorityQueueForDistancing.remove();
            String content = node.content.toString();

            Log.d(TAG, content);
            for(String dash : patternDash) {
                if(Pattern.compile(dash).matcher(content).find()) {
                    return contexts[1];
                }
            }
            for(String to : patternTo) {
                if(Pattern.compile(to).matcher(content).find()) {
                    return contexts[2];
                }
            }
            for(String minus : patternMinus) {
                if(Pattern.compile(minus).matcher(content).find()) {
                    return contexts[3];
                }
            }
        }

        return contexts[defaultIdx];
    }

}
