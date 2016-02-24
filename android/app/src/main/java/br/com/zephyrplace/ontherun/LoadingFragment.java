package br.com.zephyrplace.ontherun;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;

/**
 * A placeholder fragment containing a simple view.
 */
public class LoadingFragment extends BaseFragment {

    public LoadingFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_loading, container, false);

        AnimatorSet animations = new AnimatorSet();

        ImageView backgroundImageView = (ImageView) v.findViewById(R.id.background);
        ObjectAnimator appear = ObjectAnimator.ofFloat(backgroundImageView, "alpha", 0f, 1);
        appear.setDuration(6000);
        appear.setRepeatCount(ObjectAnimator.INFINITE);
        appear.setRepeatMode(ObjectAnimator.REVERSE);
        ObjectAnimator shake =
                ObjectAnimator.ofFloat(backgroundImageView, "translationX", 0, 25, -25, 25, -25, 15, -15, 6, -6, 0);
        appear.setDuration(1000);
        shake.setRepeatCount(ObjectAnimator.INFINITE);
        shake.setRepeatMode(ObjectAnimator.REVERSE);
        animations.playSequentially(appear, shake);
        animations.start();

        return v;
    }

}
