/*
 * Copyright (C) Profuturo All rights reserved. Todos los derechos reservados 2019.
 * mailto:
 *
 * Tramites digitales solo se puede distribuir con la autorización de Profuturo
 * License as published by Profuturo. Licencia publicada por Profuturo
 * version 1 .
 *
 * Tramites digitales is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 */
package com.mx.profuturo.android.pensionat.vistasCustomizadas;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.mx.profuturo.android.pensionat.R;
import com.mx.profuturo.android.pensionat.domain.model.CustomProgressModel;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1>CustomProgressBar</h1>
 * Clase que sirve para visualizar el componente de progreso
 * en la sección de formulario.
 *
 * @author Everis
 * @version 1.0
 * @since 2019-03-16
 */
public class CustomProgressBar extends FrameLayout {
    double textViewWidth = 0;
    float imageWidth = 0;
    List<? extends CustomProgressModel> formsModelList;
    List<ImageView> labelImageViewList = new ArrayList<>();
    List<ProgressBar> progressBarList = new ArrayList<>();
    List<LinearLayout> progressDotsLayoutList = new ArrayList<>();
    int cumulativeSectionCount = 0;
    private LayoutInflater inflater;
    private LinearLayout progressContainer;
    private LinearLayout formTextContainer;

    public CustomProgressBar(@NonNull Context context) {
        super(context);
        inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert inflater != null;
        View mView = inflater.inflate(R.layout.custom_progress_bar, this);
        progressContainer = mView.findViewById(R.id.progress_container);
        formTextContainer = mView.findViewById(R.id.form_text_container);
    }

    public CustomProgressBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert inflater != null;
        View mView = inflater.inflate(R.layout.custom_progress_bar, this);
        progressContainer = mView.findViewById(R.id.progress_container);
        formTextContainer = mView.findViewById(R.id.form_text_container);
    }

    public CustomProgressBar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert inflater != null;
        View mView = inflater.inflate(R.layout.custom_progress_bar, this);
        progressContainer = mView.findViewById(R.id.progress_container);
        formTextContainer = mView.findViewById(R.id.form_text_container);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CustomProgressBar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert inflater != null;
        View mView = inflater.inflate(R.layout.custom_progress_bar, this);
        progressContainer = mView.findViewById(R.id.progress_container);
        formTextContainer = mView.findViewById(R.id.form_text_container);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    private void setImageWidth() {
        imageWidth = labelImageViewList.get(0).getWidth();
    }

    public void addProgressForms(List<? extends CustomProgressModel> formsModelList) {
        this.formsModelList = formsModelList;
        inflateProgressViews();
    }

    private void inflateProgressViews() {
        for (int i = 0; i < formsModelList.size(); i++) {
            if (i != 0) {
                View progressLabelView = inflater.inflate(R.layout.single_progress_label, null);
                progressContainer.addView(progressLabelView);
                LinearLayout relativeLayout = (LinearLayout) ((LinearLayout) progressLabelView).getChildAt(0);
                ImageView imgCircle = (ImageView) relativeLayout.getChildAt(0);
                labelImageViewList.add(imgCircle);
            }

            cumulativeSectionCount += formsModelList.get(i).getSectionCount();

            View progressBarView = inflater.inflate(R.layout.single_progress_bar, null);
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1.0f
            );
            LinearLayout linearLayout = new LinearLayout(getContext());
            linearLayout.setLayoutParams(param);
            linearLayout.addView(progressBarView);

            if (formsModelList.size() == 1 || i < formsModelList.size() - 1) {
                progressContainer.addView(linearLayout);
            }

            RelativeLayout relativeLayout2 = (RelativeLayout) ((LinearLayout) progressBarView).getChildAt(0);
            progressDotsLayoutList.add((LinearLayout) relativeLayout2.getChildAt(0));
            progressBarList.add((ProgressBar) relativeLayout2.getChildAt(1));
        }

        progressBarList.get(0).post(() -> {
            setImageWidth();
            progressBarList.get(0).getWidth();
            progressContainer.post(() -> {
                setSectionLabels();
                progressContainer.getX();
            });
        });

        invalidate();
    }

    private void setSectionLabels() {
        formTextContainer.removeAllViews();

        for (int i = 0; i < formsModelList.size(); i++) {
            TextView view = new TextView(getContext());
            if (i != 0) {
                textViewWidth = Double.valueOf(formTextContainer.getWidth()) / Double.valueOf((double) formsModelList.size() - (double) 1);
            } else {
                textViewWidth = 0;
            }
            view.setTextColor(getResources().getColor(R.color.colorTextoSecondario));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) textViewWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
            view.setLayoutParams(params);
            view.setText(formsModelList.get(i).getLabelName());
            view.setGravity(Gravity.CENTER);
            view.setTypeface(view.getTypeface(), Typeface.NORMAL);
            view.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            formTextContainer.addView(view);

            if (i < formsModelList.size() - 1) {
                progressBarList.get(i).setMax(formsModelList.get(i).getSectionCount() * 100);
            }
        }
        setProgressDotsView();
    }

    private void setProgressDotsView() {
        for (int i = 0; i < progressBarList.size(); i++) {
            int mTotalProgressBarWidth = progressBarList.get(i).getWidth();
            int dotsCount = mTotalProgressBarWidth / 13;
            for (int j = 0; j < dotsCount; j++) {
                View view = new View(getContext());
                view.setBackgroundColor(getResources().getColor(R.color.colorTextoSecondario));
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(4, 4);
                params.setMargins(10, 0, 0, 0);
                view.setLayoutParams(params);
                progressDotsLayoutList.get(i).addView(view);
            }
        }
    }

    public void iniciarSeccion(int seccion) {
        progressBarList.get(0).post(() -> progressContainer.post(() -> {
            for (int i = 0; i < formsModelList.size(); i++) {
                TextView textView = null;
                if (formsModelList.size() >= (i + 1)) {
                    textView = (TextView) formTextContainer.getChildAt(i + 1);
                }
                if (seccion == i) {
                    progresoProgress(textView, i);
                } else {
                    cambioTexto(textView);
                }
            }
        }));
    }

    private void cambioTexto(TextView textView) {
        if (textView != null) {
            textView.setTextColor(getResources().getColor(R.color.colorTextoSecondario));
            textView.setTypeface(null, Typeface.NORMAL);
        }
    }

    private void progresoProgress(TextView textView, int i) {
        ProgressBar progressBar = progressBarList.get(i);
        int progresoActual = (progressBar.getProgress() / 100);
        if (progresoActual == formsModelList.get(i).getSectionCount()) {
            labelImageViewList.get(i).setImageResource(R.drawable.progress_label_completed);
        } else {
            labelImageViewList.get(i).setImageResource(R.drawable.edit);
        }

        if (textView != null) {
            textView.setTextColor(getResources().getColor(R.color.cerulean));
            textView.setTypeface(null, Typeface.BOLD);
        }
    }

    public void cambiarProgreso(int progreso, int seccion) {
        progressBarList.get(0).post(() -> progressContainer.post(() -> {
            for (int i = 0; i < formsModelList.size(); i++) {
                if (seccion == i) {
                    float porcentaje = 100;
                    ProgressBar progressBar = progressBarList.get(i);
                    progressBar.setProgress(progreso * (int) porcentaje);
                    ImageView imageProgress = labelImageViewList.get(i);
                    if (progreso == formsModelList.get(i).getSectionCount()) {
                        imageProgress.setImageResource(R.drawable.progress_label_completed);
                    } else {
                        imageProgress.setImageResource(R.drawable.edit);
                    }
                }
            }
        }));
    }
}
