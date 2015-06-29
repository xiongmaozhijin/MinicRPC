package com.example.minicrpc;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.example.minicrpc.databasedao.LuckyDog;
import com.example.minicrpc.db.DBManager;
import com.example.minicrpc.entity.ContactPerson;
import com.example.minicrpc.entity.LuckyPeople;
import com.example.minicrpc.fragments.FragmentBirthday;
import com.example.minicrpc.utils.MinicRPCLog;
import com.example.minicrpc.utils.MinicUtils;
import com.example.minicrpc.widget.CommonTitleBar;

/**
 * �������
 * @author michael.chen
 *
 */
public class AddLuckyDog extends Activity {
	
	private static final String TAG = "AddLuckyDog";
	private static final boolean DEBUG = MinicRPCLog.DEBUG;
	
	private static final int PROTRAIT_SIZE = 150;
	
	//on activity result request code
	private static final int REQUEST_PORTRAIT_FROM_PHOTO = 0;
	private static final int REQUEST_PORTRAIT_FROM_CUT = 1;
	private static final int REQUEST_PORTRAIT_FROM_GRALLERY = 2;
	private static final int REQUEST_NAME_ACTION = 3;
	
	
	private App mApp;
	
	
	@InjectView(R.id.title) CommonTitleBar mTitle;
	@InjectView(R.id.portrait) ImageButton mImgBtnPortrait;
	@InjectView(R.id.name_edt) EditText mEdtName;
	@InjectView(R.id.name_action) Button mBtnNameAction;
	@InjectView(R.id.rg_sex_group) RadioGroup mRgSexGroup;
	@InjectView(R.id.birthday_edt) EditText mEdtBirthday;
	@InjectView(R.id.birthday_action) Button mBtnBirthdayAction;
	@InjectView(R.id.tel_edt) EditText mEdtTel;
	@InjectView(R.id.tel_action) Button mBtnTelAction;
	@InjectView(R.id.note_edt) EditText mEdtNote;
	@InjectView(R.id.save) Button mBtnSave;
	
	//�Զ���༭ͷ��Ի���
//	@Optional @InjectView(R.id.dialog_rg) RadioGroup mBtnEditPortrait;
//	@Optional @InjectView(R.id.dialog_rb_from_gralley) RadioButton mRbGralley;
//	@Optional @InjectView(R.id.dialog_rb_from_photo) RadioButton mRbPhoto;
//	@Optional @InjectView(R.id.dialog_rb_from_recommand) RadioButton mRbRecommand;
	RadioButton mRbGralley;
	RadioButton mRbPhoto;
	RadioButton mRbRecommand;
	
	private Dialog mDialogEditPortrait;
	/** ��ʱ���ļ� */
	private File mPortraitFileName;
	/** ���յ��ļ� */
	private File mPortraitFileNameFinal;
	/** ����Ҫɾ�����ļ� */
	private List<File> mTempList = new ArrayList<File>();
	private boolean isSave = false;
	/** �Ƿ���±༭ */
	private boolean isUpdate = false;
	private long keyid = -1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addluckydog);
		
		ButterKnife.inject(this);
		
		mApp = (App) getApplication();
		
		adjustUpdate();
		
		mTitle.setOnLeftBtnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
	}
	
	/**
	 * �ж��Ƿ���±༭
	 */
	private void adjustUpdate() {
		Intent i = getIntent();
		if (i!=null) {
			isUpdate = i.getBooleanExtra(BirthdayInfo.UPDATE, false);
		}
		
		if (isUpdate) {
			long id = i.getLongExtra(FragmentBirthday.ENTITY_ID, -1);
			if (id!=-1) {
				keyid = id;
				LuckyDog luckyDog = DBManager.getInstance(this).loadByKey(id);
				mPortraitFileNameFinal = MinicUtils.getFile(luckyDog.getPortraitPath());
				setupInfo(luckyDog);
			}
		}
		
	}

	/**
	 * ���������Ϣ
	 * @param luckyDog
	 */
	private void setupInfo(final LuckyDog luckyDog) {
		mApp.getImageLoader().displayImage(MinicUtils.wrapperToImageload(luckyDog.getPortraitPath()), mImgBtnPortrait);
		mEdtName.setText(luckyDog.getName());
		if (luckyDog.getSex() == LuckyPeople.MAN) {
			mRgSexGroup.check(R.id.man);
		} else {
			mRgSexGroup.check(R.id.feman);
		}
		mEdtBirthday.setText(MinicUtils.getDate(luckyDog.getBirthday()));
		mEdtTel.setText(luckyDog.getTel());
		mEdtNote.setText(luckyDog.getNote()==null ? "" : luckyDog.getNote());
	}

	@Override
	protected void onStop() {
		super.onStop();
		
		if (isFinishing()) {
			//��������
			if (DEBUG) {
				MinicRPCLog.d(TAG, "��������");
			}
			if (!isSave) {	//û�б���
				MinicUtils.clearTempFile(mTempList, null);
			} else {
				MinicUtils.clearTempFile(mTempList, mPortraitFileNameFinal);
			}
		
		}
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if (DEBUG) {
			MinicRPCLog.d(TAG, "%s, requestCode=%d, resultCode=%d", "onActivityResult", requestCode, resultCode);
		}
		
		if (resultCode == Activity.RESULT_OK) {
			
			switch (requestCode) {
			case REQUEST_PORTRAIT_FROM_PHOTO:
				mTempList.add(mPortraitFileName);
				startPhotoZoom(Uri.fromFile(mPortraitFileName), PROTRAIT_SIZE);
				break;
			case REQUEST_PORTRAIT_FROM_CUT:
				mPortraitFileNameFinal = mPortraitFileName;
				showPortraitFromCut(data);
				break;
			case REQUEST_PORTRAIT_FROM_GRALLERY:
				if (data != null) {
					
					mPortraitFileName = MinicUtils.copyFile(this, data.getData());
					mTempList.add(mPortraitFileName);
					
					startPhotoZoom(data.getData(), PROTRAIT_SIZE);
					if (DEBUG) {
						MinicRPCLog.d(TAG, "uri=%s, path=%s", data.getDataString(), data.getData().getPath());
						String r = MinicUtils.getRealPathFromURI(this, data.getData());
						MinicRPCLog.d(TAG, "path=%s", r);
//						File srcfile = MinicUtils.getRealFileFromURI(this, data.getData());
//						MinicUtils.copyFile(srcfile, "aaa.jpg", this);
					}
				}
				break;
			case REQUEST_NAME_ACTION:
				Uri uri = data.getData();
				ContactPerson cp = MinicUtils.getContactPersonDetail(uri, this);
				if (DEBUG) {
					MinicRPCLog.d(TAG, cp.toString());
				}
				setupContactInfo(cp);
				break;
			}
			
		}
		
	}
		
		
	
	/**
	 * ����ϵ����Ϣ���ص���Ӧ�Ŀؼ���
	 * @param cp
	 */
	private void setupContactInfo(final ContactPerson cp) {
		mEdtName.setText(cp.getName());
		mEdtTel.setText(cp.getAllphones().get(0));
		if (cp.getProtrait() != null) {
			mImgBtnPortrait.setImageBitmap(cp.getProtrait());
			mPortraitFileName = mPortraitFileNameFinal = MinicUtils.saveFile(cp.getProtrait(), this);
			mTempList.add(mPortraitFileName);
		}
	}


	@OnClick(R.id.portrait)
	public void portrait() {
		if (mDialogEditPortrait == null) {
			View view = View.inflate(this, R.layout.dialog_editportrait, null);
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setView(view);
			mDialogEditPortrait = builder.create();
			mDialogEditPortrait.getWindow().setGravity(Gravity.BOTTOM);
			mDialogEditPortrait.getWindow().setWindowAnimations(R.style.CustomDialogAnin);
			
//			ButterKnife.inject(this, view);		//dont forget
			mRbGralley = (RadioButton) view.findViewById(R.id.dialog_rb_from_gralley);
			mRbPhoto = (RadioButton) view.findViewById(R.id.dialog_rb_from_photo);
			mRbRecommand = (RadioButton) view.findViewById(R.id.dialog_rb_from_recommand);
			mRbGralley.setOnClickListener(new OnListenrEditPortrait());
			mRbPhoto.setOnClickListener(new OnListenrEditPortrait());
			mRbRecommand.setOnClickListener(new OnListenrEditPortrait());
		}
		mDialogEditPortrait.show();
	}
	
	@OnClick(R.id.name_action)
	public void nameAction() {
		//����ͨѶ¼�����⿼��ͨѶ¼��ͼƬͷ��
		Intent i = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
		startActivityForResult(i, REQUEST_NAME_ACTION);
	}
	
	@OnClick(R.id.birthday_action)
	public void birthdayAction() {
		
	}
	
	@OnClick(R.id.tel_action)
	public void telAction() {
		
	}
	
	@OnClick(R.id.save)
	public void save() {
		String name = mEdtName.getText().toString();
		int rbId = mRgSexGroup.getCheckedRadioButtonId();
		int sex = rbId == R.id.man ? LuckyPeople.MAN : LuckyPeople.FEMAN;
		String birthday = mEdtBirthday.getText().toString();
		String tel = mEdtTel.getText().toString();
		String note = mEdtNote.getText().toString();

		if (TextUtils.isEmpty(name)) {
			mEdtName.setError(getResources().getString(
					R.string.luckydog_edt_name_err));
			return;
		}

		String regEx = "^\\d{4}-\\d{1,2}-\\d{1,2}$";
		Pattern pattern = Pattern.compile(regEx);
		Matcher matcher = pattern.matcher(birthday);
		if (!matcher.find()) {
			mEdtBirthday.setError(getResources().getString(
					R.string.luckydog_edt_birthday_err));
			return;
		}
		
		regEx = "^\\d{1,}";
		pattern = Pattern.compile(regEx);
		matcher = pattern.matcher(tel);
		if (!matcher.find()) {
			mEdtTel.setError(getResources().getString(R.string.luckydog_edt_tel_err));
			return;
		}
		
		isSave = true;
		String errorPath = mApp.getImageDirReal() + "/unexits.jpg";
		DBManager.getInstance(this).updateOrInsert(new LuckyDog(!isUpdate ? null : keyid, 
				mPortraitFileNameFinal == null ? errorPath : mPortraitFileNameFinal.getAbsolutePath(), 
				name, sex, MinicUtils.getDate(birthday), tel, note));
		finish();
	}
	
	
	
//	@OnClick(R.id.dialog_rb_from_gralley)
	public void editPortraitFromGrally() {
		if (DEBUG) {
			MinicRPCLog.d(TAG, "%s", "editPortraitFromGrally");
		}
		
		Intent i = new Intent(Intent.ACTION_PICK, null);
		i.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
		startActivityForResult(i, REQUEST_PORTRAIT_FROM_GRALLERY);
		
	}
	
//	@OnClick(R.id.dialog_rb_from_photo)
	public void editPortraitFromPhoto() {
		mPortraitFileName = MinicUtils.getUniqueImageFileNameReal(this);
		Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mPortraitFileName) );
		startActivityForResult(i, REQUEST_PORTRAIT_FROM_PHOTO);
	}
	
//	@OnClick(R.id.dialog_rb_from_recommand) 
	public void editPortraitFromRecommand() {
		
	}
	
	/**
	 * ͼƬ�ü�
	 * @param uri
	 * @param size
	 */
	private void startPhotoZoom(Uri uri, int size) {
		Intent i = new Intent("com.android.camera.action.CROP");
		i.setDataAndType(uri, "image/*");
		i.putExtra("crop", true);
		i.putExtra("aspectX", 1);
		i.putExtra("aspectY", 1);
		i.putExtra("outputX", size);
		i.putExtra("outputY", size);
		i.putExtra("return-data", true);
		startActivityForResult(i, REQUEST_PORTRAIT_FROM_CUT);
	}
	
	
	private void showPortraitFromCut(Intent i) {
		Bundle bundle = i.getExtras();
		
		if (bundle != null) {
			Bitmap photo = bundle.getParcelable("data");
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			photo.compress(Bitmap.CompressFormat.JPEG, 75, bos);
			
			Bitmap bitmap = BitmapFactory.decodeByteArray(bos.toByteArray(), 0, bos.size());
			mImgBtnPortrait.setImageBitmap(bitmap);
			
			if (DEBUG) {
				MinicRPCLog.d(TAG, "ѹ��ǰ���ֽڴ�С-%d, �߶�-%d, ���-%d ", photo.getByteCount(), photo.getHeight(), photo.getWidth());
				MinicRPCLog.d(TAG, "ѹ�����ֽڴ�С-%d, �߶�-%d, ���-%d, �������С�� ", bitmap.getByteCount(), bitmap.getHeight(), bitmap.getWidth(), bos.size());
			}
			
		}
		
		
	}
	
	
	class OnListenrEditPortrait implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.dialog_rb_from_gralley:
				editPortraitFromGrally();
				break;
			case R.id.dialog_rb_from_photo:
				editPortraitFromPhoto();
				break;
			case R.id.dialog_rb_from_recommand:
				editPortraitFromRecommand();
				break;
			}
		}
		
	}
	
	
	
}

