package com.fooda.wadalzaki.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.Editable;

import com.fooda.wadalzaki.model.UserInfo;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Utility {
	public static final String IMAGE_BYTE ="image" ;
	public static final String PERSON_NAME ="name" ;
	public static final String PERSON_NUMBER ="number" ;
	//List<Person> models;
	// convert from bitmap to byte array
	public static byte[] getBytes(Bitmap bitmap) {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.PNG, 0, stream);
		return stream.toByteArray();
	}

	// convert from byte array to bitmap
	public static Bitmap getPhoto(byte[] image) {
		return BitmapFactory.decodeByteArray(image, 0, image.length);
	}
	public static Bitmap uriToBitmap(Uri pickedImage, Context context)  {
		// Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), pickedImage);

		// Let's read picked image path using content resolver
		String[] filePath = { MediaStore.Images.Media.DATA };
		Cursor cursor = context.getContentResolver().query(pickedImage, filePath, null, null, null);
		cursor.moveToFirst();
		@SuppressLint("Range") String imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));

		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inPreferredConfig = Bitmap.Config.ARGB_8888;
		Bitmap bitmap = BitmapFactory.decodeFile(imagePath, options);

		// Do something with the bitmap


		// At the end remember to close the cursor or you will end with the RuntimeException!
		cursor.close();
		return bitmap;
	}


	/*public static List<Person> getAllModels(){
		List<Person> models;
		models=new ArrayList<>();
		models.add(new Person("Ahmed","0967654367",R.drawable.a8));
		models.add(new Person("Ali","0914732277", R.drawable.a1));
		models.add(new Person("Asad","0987655433",R.drawable.a2));
		models.add(new Person("Aya","0976663333",R.drawable.a4));
		models.add(new Person("bador","0967654367",R.drawable.a8));
		models.add(new Person("basil","0967654367",R.drawable.a8));
		models.add(new Person("car","0967654367",R.drawable.a8));
		models.add(new Person("cnn","0967654367",R.drawable.a8));
		models.add(new Person("dalia","0967654367",R.drawable.a8));
		models.add(new Person("dia","0967654367",R.drawable.a8));
		models.add(new Person("dog","0967654367",R.drawable.a8));
		models.add(new Person("essa","0967654367",R.drawable.a8));
		models.add(new Person("Faris","0127876543",R.drawable.a7));
		models.add(new Person("fathi","0904022209",R.drawable.a9));
		models.add(new Person("fatima","0904022209",R.drawable.a9));

		models.add(new Person("fcgg","0904022209",R.drawable.a9));
		models.add(new Person("Fdggg","0904022209",R.drawable.a9));
		models.add(new Person("Fooda","0904022209",R.drawable.a9));
		models.add(new Person("Gasim","0904038538",R.drawable.a6));
		models.add(new Person("gass","0967654367",R.drawable.a8));
		models.add(new Person("hala","0967654367",R.drawable.a8));
		models.add(new Person("hanan","0967654367",R.drawable.a8));
		models.add(new Person("Hani","0994556654",R.drawable.a5));
		models.add(new Person("hasim","0967654367",R.drawable.a8));
		models.add(new Person("in","0967654367",R.drawable.a8));
		models.add(new Person("ishag","0967654367",R.drawable.a8));
		models.add(new Person("jalal","0967654367",R.drawable.a8));
		models.add(new Person("jamal","0967654367",R.drawable.a8));
		models.add(new Person("jana","0967654367",R.drawable.a8));
		models.add(new Person("lama","0967654367",R.drawable.a8));
		models.add(new Person("leeza","0967654367",R.drawable.a8));
		models.add(new Person("londa","0967654367",R.drawable.a8));
		models.add(new Person("mahmood","0967654367",R.drawable.a8));
		models.add(new Person("malak","0967654367",R.drawable.a8));
		models.add(new Person("mamar","0967654367",R.drawable.a8));
		models.add(new Person("mazin","0967654367",R.drawable.a8));
		models.add(new Person("mohmed","0967654367",R.drawable.a8));
		models.add(new Person("mortada","0967654367",R.drawable.a8));
		models.add(new Person("mozan","0967654367",R.drawable.a8));
		models.add(new Person("Musa","0987655636",R.drawable.a3));






		return models;
	}*/
	public static List<UserInfo> searchForPerson(List<UserInfo>list, Editable s) {
		List<UserInfo> newList = new ArrayList<>();
		String ss = s.toString();
		for (int i = 0; i < list.size(); i++) {
			UserInfo person = list.get(i);
			if(ss.matches("^[0-9]+")) {//号码
				if (person.getPhoneNumber().contains(ss)) {
					newList.add(person);
				}
			}else {//名字
				if(person.getName().contains(ss)){
					newList.add(person);
				}
			}
		}
		return newList;
	}
}
