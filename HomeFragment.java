package com.healthwellness.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.healthwellness.R;
import com.healthwellness.Activity.StartActivity;
import com.healthwellness.Model.Music;
import com.healthwellness.Model.Music_Data;
import com.healthwellness.Retrofit.APIService;
import com.healthwellness.Retrofit.ApiUtils;
import com.healthwellness.helper.Internet_Alert;
import com.healthwellness.helper.NetworkUtils;
import com.healthwellness.helper.Utils;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements View.OnClickListener {

    View view;
    ImageView iamge1, iamge2,iamge3, iamge4, iamge5, iamge6;
    Animation animation;

    APIService mApiservice;
    ArrayList<String> list_music = new ArrayList<>();
    ArrayList<String> list_image = new ArrayList<>();
    ArrayList<Music_Data> music_data = new ArrayList<>();
    String MusicPath1,MusicPath2,MusicPath3,MusicPath4,MusicPath5;
    String ImagePath1,ImagePath2,ImagePath3,ImagePath4,ImagePath5;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);


        if (NetworkUtils.isNetworkConnected(getContext())) {


            mApiservice = ApiUtils.getAPIService();

            init();
            Get_SoundList();


            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    iamge2.startAnimation(animation);
                    iamge3.startAnimation(animation);
                    iamge4.startAnimation(animation);
                    iamge5.startAnimation(animation);
                    iamge6.startAnimation(animation);


                    iamge2.setVisibility(View.VISIBLE);
                    iamge3.setVisibility(View.VISIBLE);
                    iamge4.setVisibility(View.VISIBLE);
                    iamge5.setVisibility(View.VISIBLE);
                    iamge6.setVisibility(View.VISIBLE);
                }
            }, 10000);



        } else {
            Internet_Alert.alertDialogShow(getContext(), "" + getString(R.string.network_error));
        }
        return view;
    }

    private void init() {

        iamge2 = view.findViewById(R.id.iamge2);
        iamge3 = view.findViewById(R.id.iamge3);
        iamge4 = view.findViewById(R.id.iamge4);
        iamge5 = view.findViewById(R.id.iamge5);
        iamge6 = view.findViewById(R.id.iamge6);

        animation = AnimationUtils.loadAnimation(getActivity(), R.anim.better_pop);

        PushDownAnim.setPushDownAnimTo(iamge2).setOnClickListener(this);
        PushDownAnim.setPushDownAnimTo(iamge3).setOnClickListener(this);
        PushDownAnim.setPushDownAnimTo(iamge4).setOnClickListener(this);
        PushDownAnim.setPushDownAnimTo(iamge5).setOnClickListener(this);
        PushDownAnim.setPushDownAnimTo(iamge6).setOnClickListener(this);

    }

    private void Get_SoundList() {

        mApiservice.MUSIC_CALL().enqueue(new Callback<Music>() {
            @Override
            public void onResponse(Call<Music> call, Response<Music> response) {



                if (response.body().getStatus()==1){

                    music_data.addAll(response.body().getData());

                    for (int i=0; i<music_data.size(); i++){
                        list_music.add(music_data.get(i).getAudio());
                        list_image.add(music_data.get(i).getPicture());
                    }

                    MusicPath1 = list_music.get(0);
                    MusicPath2 = list_music.get(1);
                    MusicPath3 = list_music.get(2);
                    MusicPath4 = list_music.get(3);
                    MusicPath5 = list_music.get(4);

                    ImagePath1 = list_image.get(0);
                    ImagePath2 = list_image.get(1);
                    ImagePath3 = list_image.get(2);
                    ImagePath4 = list_image.get(3);
                    ImagePath5 = list_image.get(4);

                }
                if (response.body().getStatus()==0){
                    Toast.makeText(getActivity(), ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Music> call, Throwable t) {
                Utils.print("SoundListResponse",""+t);
            }
        });
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.iamge2:
                iamge2.startAnimation(animation);
                Intent intent = new Intent(getActivity(), StartActivity.class);
                intent.putExtra("MUSIC",MusicPath1);
                intent.putExtra("IMAGE",ImagePath1);
                getActivity().startActivity(intent);
              break;
            case R.id.iamge3:

                iamge3.startAnimation(animation);
                Intent intent1 = new Intent(getActivity(), StartActivity.class);
                intent1.putExtra("MUSIC",MusicPath2);
                intent1.putExtra("IMAGE",ImagePath2);
                getActivity().startActivity(intent1);
                break;

            case R.id.iamge4:

                iamge4.startAnimation(animation);
                Intent intent2 = new Intent(getActivity(), StartActivity.class);
                intent2.putExtra("MUSIC",MusicPath3);
                intent2.putExtra("IMAGE",ImagePath3);
                getActivity().startActivity(intent2);
                break;

            case R.id.iamge5:

                iamge5.startAnimation(animation);
                Intent intent3 = new Intent(getActivity(), StartActivity.class);
                intent3.putExtra("MUSIC",MusicPath4);
                intent3.putExtra("IMAGE",ImagePath4);
                getActivity().startActivity(intent3);
                break;
            case R.id.iamge6:

                iamge6.startAnimation(animation);
                Intent intent4 = new Intent(getActivity(), StartActivity.class);
                intent4.putExtra("MUSIC",MusicPath5);
                intent4.putExtra("IMAGE",ImagePath5);
                getActivity().startActivity(intent4);
                break;

        }
    }
}