package com.sanjnan.shopping.apps;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

abstract public class AbstractActivity
        extends AppCompatActivity
        implements LocationManager.Listener,
        GoogleApiClient.OnConnectionFailedListener {

    protected GoogleApiClient googleApiClient;
    protected FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    protected Uri imageUri;

    protected void initialize(int webClientId) {

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(webClientId))
                .requestEmail()
                .build();
        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void initializationComplete() {

        signIn();
    }
    abstract protected void postSuccessfulPermission();

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(signInIntent, Constants.SIGNIN_ACTIVITY_CODE);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public void showProgressDialog(final int resource) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if (progressDialog == null) {
                    progressDialog = new ProgressDialog(AbstractActivity.this);
                }
                if (!progressDialog.isShowing()) {
                    Resources res = getResources();
                    try {

                        View view = getLayoutInflater().inflate(R.layout.progress_dialog, null);
                        progressDialog.setView(view);
                        progressDialog.setIndeterminateDrawable(Drawable.createFromXml(res, res.getXml(R.xml.sanjnanthrobber)));
                        progressDialog.setMessage(getResources().getString(resource));
                        {
                            TextView textView = (TextView)view.findViewById(R.id.message);
                            textView.setText(resource);
                        }
                        progressDialog.setTitle(resource);
                    } catch (XmlPullParserException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    progressDialog.show();
                }
            }
        });
    }
    public void hideProgressDialog() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.cancel();
                    progressDialog = null;
                }
            }
        });
    }
    public void showExitDialog(final ExitReason exitReason) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
// Add the buttons
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
                // User clicked OK button
            }
        });
        switch(exitReason) {
            default:
                builder.setNegativeButton(R.string.request_permission, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        handleNegativeResponse(exitReason);
                    }
                });
                builder.setMessage(ExitReason.REQUIRED_PERMISSIONS_NOT_PRESENT.getReason());
                break;
        }
        builder.create().show();
    }
    protected void handleNegativeResponse(ExitReason exitReason) {
        switch(exitReason) {
            case REQUIRED_PERMISSIONS_NOT_PRESENT:
                requestMandatoryPermissions();
                break;
        }
    }
    protected void requestMandatoryPermissions() {

        List<String> permissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.GET_ACCOUNTS) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(android.Manifest.permission.GET_ACCOUNTS);
        }
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(android.Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(android.Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.CAMERA);
        }
        if (permissionList.size() > 0) {

            ActivityCompat.requestPermissions(this, permissionList.toArray(new String[permissionList.size()]),
                    PermissionRequestConstants.ALL_PERMISSIONS_REQUEST);
        }
        else {
            postSuccessfulPermission();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case PermissionRequestConstants.ALL_PERMISSIONS_REQUEST: {

                for (int i = 0; i < grantResults.length; ++i) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        showExitDialog(ExitReason.REQUIRED_PERMISSIONS_NOT_PRESENT);
                        return;
                    }
                }
                postSuccessfulPermission();
            }
        }
    }
    public void dispatchTakeSpecificPictureIntent(Activity activity, String applicationId, int requestCode) {
        dispatchTakeSpecificPictureIntent(activity, applicationId, requestCode, new Bundle());
    }
    public void dispatchTakeSpecificPictureIntent(Activity activity, String applicationId, int requestCode, Bundle bundle) {
        String authority = applicationId + ".camera.fileprovider";
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(activity.getPackageManager()) != null) {
            imageUri = FileProvider.getUriForFile(activity, authority, FileUtils.getTempFile(activity));
            takePictureIntent.putExtras(bundle);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(takePictureIntent, requestCode);
        }
    }
    public void dispatchPickSpecificPictureIntent(int requestCode) {

        dispatchPickSpecificPictureIntent(requestCode, new Bundle());
    }
    public void dispatchPickSpecificPictureIntent(int requestCode, Bundle bundle) {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.putExtras(bundle);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), requestCode);
    }
}
