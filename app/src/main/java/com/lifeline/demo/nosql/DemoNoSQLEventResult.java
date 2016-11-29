package com.lifeline.demo.nosql;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amazonaws.AmazonClientException;
import com.amazonaws.mobile.AWSMobileClient;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.models.nosql.EventDO;

import java.util.Set;

public class DemoNoSQLEventResult implements DemoNoSQLResult {
    private static final int KEY_TEXT_COLOR = 0xFF333333;
    private final EventDO result;

    DemoNoSQLEventResult(final EventDO result) {
        this.result = result;
    }
    @Override
    public void updateItem() {
        final DynamoDBMapper mapper = AWSMobileClient.defaultMobileClient().getDynamoDBMapper();
        final String originalValue = result.getDate();
        result.setDate(DemoSampleDataGenerator.getRandomSampleString("date"));
        try {
            mapper.save(result);
        } catch (final AmazonClientException ex) {
            // Restore original data if save fails, and re-throw.
            result.setDate(originalValue);
            throw ex;
        }
    }

    @Override
    public void deleteItem() {
        final DynamoDBMapper mapper = AWSMobileClient.defaultMobileClient().getDynamoDBMapper();
        mapper.delete(result);
    }

    private void setKeyTextViewStyle(final TextView textView) {
        textView.setTextColor(KEY_TEXT_COLOR);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
        layoutParams.setMargins(dp(5), dp(2), dp(5), 0);
        textView.setLayoutParams(layoutParams);
    }

    /**
     * @param dp number of design pixels.
     * @return number of pixels corresponding to the desired design pixels.
     */
    private int dp(int dp) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        return dp * (metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }
    private void setValueTextViewStyle(final TextView textView) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
        layoutParams.setMargins(dp(15), 0, dp(15), dp(2));
        textView.setLayoutParams(layoutParams);
    }

    private void setKeyAndValueTextViewStyles(final TextView keyTextView, final TextView valueTextView) {
        setKeyTextViewStyle(keyTextView);
        setValueTextViewStyle(valueTextView);
    }

    private static String bytesToHexString(byte[] bytes) {
        final StringBuilder builder = new StringBuilder();
        builder.append(String.format("%02X", bytes[0]));
        for(int index = 1; index < bytes.length; index++) {
            builder.append(String.format(" %02X", bytes[index]));
        }
        return builder.toString();
    }

    private static String byteSetsToHexStrings(Set<byte[]> bytesSet) {
        final StringBuilder builder = new StringBuilder();
        int index = 0;
        for (byte[] bytes : bytesSet) {
            builder.append(String.format("%d: ", ++index));
            builder.append(bytesToHexString(bytes));
            if (index < bytesSet.size()) {
                builder.append("\n");
            }
        }
        return builder.toString();
    }

    @Override
    public View getView(final Context context, final View convertView, int position) {
        final LinearLayout layout;
        final TextView resultNumberTextView;
        final TextView userIdKeyTextView;
        final TextView userIdValueTextView;
        final TextView nameKeyTextView;
        final TextView nameValueTextView;
        final TextView dateKeyTextView;
        final TextView dateValueTextView;
        final TextView descriptionKeyTextView;
        final TextView descriptionValueTextView;
        final TextView friendsIdKeyTextView;
        final TextView friendsIdValueTextView;
        final TextView locationKeyTextView;
        final TextView locationValueTextView;
        if (convertView == null) {
            layout = new LinearLayout(context);
            layout.setOrientation(LinearLayout.VERTICAL);
            resultNumberTextView = new TextView(context);
            resultNumberTextView.setGravity(Gravity.CENTER_HORIZONTAL);
            layout.addView(resultNumberTextView);


            userIdKeyTextView = new TextView(context);
            userIdValueTextView = new TextView(context);
            setKeyAndValueTextViewStyles(userIdKeyTextView, userIdValueTextView);
            layout.addView(userIdKeyTextView);
            layout.addView(userIdValueTextView);

            nameKeyTextView = new TextView(context);
            nameValueTextView = new TextView(context);
            setKeyAndValueTextViewStyles(nameKeyTextView, nameValueTextView);
            layout.addView(nameKeyTextView);
            layout.addView(nameValueTextView);

            dateKeyTextView = new TextView(context);
            dateValueTextView = new TextView(context);
            setKeyAndValueTextViewStyles(dateKeyTextView, dateValueTextView);
            layout.addView(dateKeyTextView);
            layout.addView(dateValueTextView);

            descriptionKeyTextView = new TextView(context);
            descriptionValueTextView = new TextView(context);
            setKeyAndValueTextViewStyles(descriptionKeyTextView, descriptionValueTextView);
            layout.addView(descriptionKeyTextView);
            layout.addView(descriptionValueTextView);

            friendsIdKeyTextView = new TextView(context);
            friendsIdValueTextView = new TextView(context);
            setKeyAndValueTextViewStyles(friendsIdKeyTextView, friendsIdValueTextView);
            layout.addView(friendsIdKeyTextView);
            layout.addView(friendsIdValueTextView);

            locationKeyTextView = new TextView(context);
            locationValueTextView = new TextView(context);
            setKeyAndValueTextViewStyles(locationKeyTextView, locationValueTextView);
            layout.addView(locationKeyTextView);
            layout.addView(locationValueTextView);
        } else {
            layout = (LinearLayout) convertView;
            resultNumberTextView = (TextView) layout.getChildAt(0);

            userIdKeyTextView = (TextView) layout.getChildAt(1);
            userIdValueTextView = (TextView) layout.getChildAt(2);

            nameKeyTextView = (TextView) layout.getChildAt(3);
            nameValueTextView = (TextView) layout.getChildAt(4);

            dateKeyTextView = (TextView) layout.getChildAt(5);
            dateValueTextView = (TextView) layout.getChildAt(6);

            descriptionKeyTextView = (TextView) layout.getChildAt(7);
            descriptionValueTextView = (TextView) layout.getChildAt(8);

            friendsIdKeyTextView = (TextView) layout.getChildAt(9);
            friendsIdValueTextView = (TextView) layout.getChildAt(10);

            locationKeyTextView = (TextView) layout.getChildAt(11);
            locationValueTextView = (TextView) layout.getChildAt(12);
        }

        resultNumberTextView.setText(String.format("#%d", + position+1));
        userIdKeyTextView.setText("userId");
        userIdValueTextView.setText(result.getUserId());
        nameKeyTextView.setText("name");
        nameValueTextView.setText(result.getName());
        dateKeyTextView.setText("date");
        dateValueTextView.setText(result.getDate());
        descriptionKeyTextView.setText("description");
        descriptionValueTextView.setText(result.getDescription());
        friendsIdKeyTextView.setText("friendsId");
        friendsIdValueTextView.setText(result.getFriendsId().toString());
        locationKeyTextView.setText("location");
        locationValueTextView.setText(result.getLocation());
        return layout;
    }
}
