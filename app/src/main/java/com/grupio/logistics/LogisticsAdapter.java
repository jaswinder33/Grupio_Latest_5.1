package com.grupio.logistics;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.grupio.R;
import com.grupio.Utils.Utility;
import com.grupio.base.SimpleBaseListAdapter;
import com.grupio.data.AttendeesData;
import com.grupio.data.ExhibitorData;
import com.grupio.data.LogisticsData;
import com.grupio.data.ScheduleData;
import com.grupio.data.SpeakerData;
import com.grupio.data.SponsorData;

/**
 * Created by JSN on 4/8/16.
 */
public class LogisticsAdapter<T> extends SimpleBaseListAdapter<LogisticsData, LogisticsAdapter.Holder> {

    private T folderType;

    public LogisticsAdapter(Context context, T type) {
        super(context);
        folderType = type;
    }

    @Override
    public int getLayout() {
        return R.layout.resource_link;
    }

    @Override
    public Holder setViewHolder(View convertView) {
        return new Holder(convertView);
    }

    @Override
    public void getView(int position, LogisticsAdapter.Holder holder) {
        mHolder.fileNameTxt.setText(getItem(position).getName());

        mHolder.viewBtn.setOnClickListener(v -> handleResourceView(position));
        mHolder.downloadBtn.setOnClickListener(v -> handleDownloadResource(position));


        String type = android.webkit.MimeTypeMap.getFileExtensionFromUrl(getItem(position).getUrl());

//        String type = getItem(position).getType();
        if (type.equalsIgnoreCase("pdf")) {
            mHolder.resourceImage.setBackgroundResource(R.drawable.pdf_icon);
        } else if (type.equalsIgnoreCase("ppt")) {
            mHolder.resourceImage.setBackgroundResource(R.drawable.ppt_small_icon);
        } else if (type.equalsIgnoreCase("doc") || type.equalsIgnoreCase("docx")) {
            mHolder.resourceImage.setBackgroundResource(R.drawable.doc);
        } else if (type.equalsIgnoreCase("xls") || type.equalsIgnoreCase("xlsx")) {
            mHolder.resourceImage.setBackgroundResource(R.drawable.xl);
        } else if (getItem(position).getUrl().contains("youtube")) {
            mHolder.resourceImage.setBackgroundResource(R.drawable.youtube);
        } else {
            mHolder.resourceImage.setBackgroundResource(R.drawable.web_icon);
        }

        if (!Utility.isValidType(type)) {
            mHolder.buttons.setVisibility(View.GONE);
        }

    }

    public void handleResourceView(int position) {
        if (folderType instanceof AttendeesData) {
            DocumentController<AttendeesData, LogisticsData> mController = new DocumentController<>(new AttendeesData(), new LogisticsData(), getContext());
            mController.viewDoc(getItem(position));
        } else if (folderType instanceof SpeakerData) {
            DocumentController<SpeakerData, LogisticsData> mController = new DocumentController<>(new SpeakerData(), new LogisticsData(), getContext());
            mController.viewDoc(getItem(position));
        } else if (folderType instanceof ExhibitorData) {
            DocumentController<ExhibitorData, LogisticsData> mController = new DocumentController<>(new ExhibitorData(), new LogisticsData(), getContext());
            mController.viewDoc(getItem(position));
        } else if (folderType instanceof LogisticsData) {
            DocumentController<LogisticsData, LogisticsData> mController = new DocumentController<>(new LogisticsData(), new LogisticsData(), getContext());
            mController.viewDoc(getItem(position));
        } else if (folderType instanceof ScheduleData) {
            DocumentController<ScheduleData, LogisticsData> mController = new DocumentController<>(new ScheduleData(), new LogisticsData(), getContext());
            mController.viewDoc(getItem(position));
        } else if (folderType instanceof SponsorData) {
            DocumentController<SponsorData, LogisticsData> mController = new DocumentController<>(new SponsorData(), new LogisticsData(), getContext());
            mController.viewDoc(getItem(position));
        }
    }

    public void handleDownloadResource(int position) {
        if (folderType instanceof AttendeesData) {
            DocumentController<AttendeesData, LogisticsData> mController = new DocumentController<>(new AttendeesData(), new LogisticsData(), getContext());
            mController.downloadResource(getItem(position));
        } else if (folderType instanceof SpeakerData) {
            DocumentController<SpeakerData, LogisticsData> mController = new DocumentController<>(new SpeakerData(), new LogisticsData(), getContext());
            mController.downloadResource(getItem(position));
        } else if (folderType instanceof ExhibitorData) {
            DocumentController<ExhibitorData, LogisticsData> mController = new DocumentController<>(new ExhibitorData(), new LogisticsData(), getContext());
            mController.downloadResource(getItem(position));
        } else if (folderType instanceof LogisticsData) {
            DocumentController<LogisticsData, LogisticsData> mController = new DocumentController<>(new LogisticsData(), new LogisticsData(), getContext());
            mController.downloadResource(getItem(position));
        } else if (folderType instanceof ScheduleData) {
            DocumentController<ScheduleData, LogisticsData> mController = new DocumentController<>(new ScheduleData(), new LogisticsData(), getContext());
            mController.downloadResource(getItem(position));
        } else if (folderType instanceof SponsorData) {
            DocumentController<SponsorData, LogisticsData> mController = new DocumentController<>(new SponsorData(), new LogisticsData(), getContext());
            mController.viewDoc(getItem(position));
        }
    }

    public class Holder {
        private ImageView resourceImage;
        private TextView fileNameTxt;
        private LinearLayout buttons;
        private Button viewBtn, downloadBtn, shareBtn;

        public Holder(View view) {
            resourceImage = (ImageView) view.findViewById(R.id.resource_image);
            fileNameTxt = (TextView) view.findViewById(R.id.map_name);
            buttons = (LinearLayout) view.findViewById(R.id.buttonLayout);
            viewBtn = (Button) view.findViewById(R.id.resourceViewbutton);
            downloadBtn = (Button) view.findViewById(R.id.resourceDownloadbutton);
            shareBtn = (Button) view.findViewById(R.id.shareDownloadbutton);

        }
    }


}
