package com.dal.canadatourism;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.GenericHandler;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class Profile extends Fragment {

    private static final String TAG = "ProfileFragment";
    public ImageView profile_picture, edit_bio, edit_genre;
    private TextView bio;
    private TextView msg;
    //    private static Bitmap[] sendBitmap;
    private static ArrayList<Bitmap> sendBitmap = new ArrayList<>();


    private static ArrayList<String> bookimglist_tosend = new ArrayList<>();

    Button btn,btn1, btn2;
    private Object ContentResolver;


    GridView gridView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login, container, false);
//        profile_picture = view.findViewById(R.id.profile_picture);
//
//
//        name = view.findViewById(R.id.text1);
        btn = view.findViewById(R.id.login);
        btn1 = view.findViewById(R.id.signup);
        btn2 = view.findViewById(R.id.logout);
        msg = view.findViewById(R.id.textView15);

        SharedPreferences pref = (getActivity()).getSharedPreferences("login",0); // 0 - for private mode
        int saved = pref.getInt("saved",0);

        if(saved == 1){
            btn.setVisibility(View.INVISIBLE);
            btn1.setVisibility(View.INVISIBLE);
            btn2.setVisibility(View.VISIBLE);
            msg.setText("You are logged-in already!");
            Toast.makeText(getActivity(), "Already Logged In!", Toast.LENGTH_SHORT).show();
        }

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentStart = new Intent(getActivity(), Login.class);
                startActivity(intentStart);
//        finish();
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentStart = new Intent(getActivity(), Signup.class);
                startActivity(intentStart);
//        finish();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Cognito cognito = new Cognito(getContext());
                CognitoUserPool pool = cognito.getPool();
                if (pool != null) {
                    //Toast.makeText(getContext(), "pool", Toast.LENGTH_SHORT).show();
                    CognitoUser user = pool.getCurrentUser();
                    if (user != null) {
                        //Toast.makeText(getContext(), "user", Toast.LENGTH_SHORT).show();
                        GenericHandler handler = new GenericHandler() {

                            @Override
                            public void onSuccess() {
                                Toast.makeText(getContext(), "Logged out!", Toast.LENGTH_SHORT).show();
                                SharedPreferences pref = getContext().getSharedPreferences("login",0); // 0 - for private mode
                                SharedPreferences.Editor editor = pref.edit();
                                editor.putInt("saved",0);
                                editor.putString("token","null");
                                editor.commit();

                                btn.setVisibility(View.VISIBLE);
                                btn1.setVisibility(View.VISIBLE);
                                btn2.setVisibility(View.INVISIBLE);
                                msg.setText("Select an action!");
                            }

                            @Override
                            public void onFailure(Exception e) {
                                Toast.makeText(getContext(), ""+e, Toast.LENGTH_SHORT).show();
                            }
                        };
                        user.globalSignOutInBackground(handler);
                    }
                }

//        finish();
            }
        });

     /*   profile_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateImage(getContext());
            }
        });



     /*   ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Profile");

        //Firebase data retrieval
        FirebaseUser curUser = FirebaseAuth.getInstance().getCurrentUser();
        String uid = curUser.getUid();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = firebaseDatabase.getReference("users");
        databaseReference.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //to retrieve bio from firebase
                bio.setText(dataSnapshot.child("bio").getValue(String.class));
                //to retrieve name from firebase
                name.setText(dataSnapshot.child("name").getValue(String.class));

                //to retrieve profile picture from firebase
                String encodedImage = dataSnapshot.child("dp").getValue(String.class);
                if (encodedImage != null) {
                    byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    profile_picture.setImageBitmap(decodedByte);
                }

                //to retrieve favorite genres from firebase
                List<String> genre_list = new ArrayList<>();
                for (DataSnapshot ds : dataSnapshot.child("genres").getChildren()) {
                    String genres = ds.getValue(String.class);
                    genre_list.add(genres);
//                    Log.d("Genre", genres);
                }

                //printing favorite tags on profile page
                chipGroup.removeAllViews();
                LayoutInflater layoutInflater = LayoutInflater.from(getContext());
                int i = 0;
                Log.d("genre_count", String.valueOf(i));
                while (i < genre_list.size()) {
                    Chip chip = (Chip) layoutInflater.inflate(R.layout.layout_selected_genres, null, false);
                    chip.setText(genre_list.get(i));
                    chipGroup.removeView(getView());
                    chipGroup.addView(chip);
                    i++;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //to retrieve book Images from firebase
        DatabaseReference bookReference = firebaseDatabase.getReference("books");
        bookReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<String> book_img_list = new ArrayList<>();
                String book_img_string, key;
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    book_img_string = ds.child("Image").getValue(String.class);
                    book_img_list.add(book_img_string);
                    Log.d("book_string", String.valueOf(book_img_string));
                }
//                bookimglist_tosend = book_img_list;
                Bitmap decodedByte = null;
                ArrayList<String> encodedImage = new ArrayList<>();
                encodedImage = book_img_list;
//                String encodedImage = book_img_list.get(0);
                int i = 0;
                while (i < encodedImage.size()) {
                    byte[] decodedString = Base64.decode(encodedImage.get(i), Base64.DEFAULT);
                    decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
//                    test.setImageBitmap(decodedByte);
//                    testImage = test;
                    sendBitmap.add(decodedByte);
                    i++;
                }
//                sendBitmap = decodedByte;


//                Log.d("bookimgsize", String.valueOf(book_img_list.size()));
                Log.d("bookimgarray", String.valueOf(book_img_list));
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });


    }

    public static ArrayList<Bitmap> getBookBitmap() {
        return sendBitmap;
    }

    public View onCreate(LayoutInflater inflater, ViewGroup container,
                         Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        gridView = (GridView) view.findViewById(R.id.grid_library);
        return view;

    }

    public static ArrayList getImageArrayList() {
        return bookimglist_tosend;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

*/
        return view;
}
    //To Update profile picture
    private void updateImage(Context context) {
        final CharSequence[] options = { "Open Camera", "Choose from Gallery","Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Update Your Profile Picture");
        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Open Camera")) {
                    Intent clickPicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(clickPicture, 0);

                } else if (options[item].equals("Choose from Gallery")) {
                    Intent choosePicture = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(choosePicture , 1);

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {
                        Bitmap clickedImage = (Bitmap) data.getExtras().get("data");
                        profile_picture.setImageBitmap(clickedImage);

                       /* FirebaseUser curUser = FirebaseAuth.getInstance().getCurrentUser();
                        String uid = curUser.getUid();
                        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                        final DatabaseReference databaseReference = firebaseDatabase.getReference("users");
                        DatabaseReference ref=databaseReference.child(uid);
*/
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        clickedImage.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                        byte[] byteFormat = stream.toByteArray();
                        String encodedImage = Base64.encodeToString(byteFormat, Base64.NO_WRAP);

                        Map<String, Object> update = new HashMap<String,Object>();
                        update.put("dp", encodedImage);
                         // ref.updateChildren(update);
                    }
                    break;
                case 1:
                    if (resultCode == RESULT_OK && data != null) {
                        Uri selectedImage = data.getData();
                        profile_picture.setImageURI(selectedImage);

                        Bitmap bitmap = null;
                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(this.getContext().getContentResolver(), selectedImage);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                     /*   FirebaseUser curUser = FirebaseAuth.getInstance().getCurrentUser();
                        String uid = curUser.getUid();
                        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                        final DatabaseReference databaseReference = firebaseDatabase.getReference("users");
                        DatabaseReference ref=databaseReference.child(uid);
*/
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                        byte[] byteFormat = stream.toByteArray();
                        String encodedImage = Base64.encodeToString(byteFormat, Base64.NO_WRAP);


                    }
                    break;
            }
        }
    }
    //profile picture updation code ends here
}
