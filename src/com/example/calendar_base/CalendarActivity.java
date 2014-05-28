package com.example.calendar_base;





import java.util.Calendar;



import android.os.Bundle;
import android.preference.Preference;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

public class CalendarActivity extends Activity {
	static String date = "";
	String datelist ="";
//	static int waitflag = 0;

	String message = "";
	CreateProductHelper helper = null;
	SQLiteDatabase db = null;
//	helper = new CreateProductHelper(CalendarActivity.this);
//	db = helper.getWritableDatabase();		//db file �����

	private static final String FILE_NAME = "CalendarFile";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calendar);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.calendar, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.item1:


			showDatePickerDialog();
//			while(waitflag ==0){
//			}
//	        databasein();
//	        waitflag=0;

			return true;
		case R.id.item2:
//			SharedPreferences preference = getSharedPreferences(FILE_NAME,
//					MODE_PRIVATE);
//			date += preference.getString("DATE", "����܂���")+"\n";

			showDialog(date);

			Log.e("DATE", date);

			return true;
		}
		return true;
	}

	private void showDatePickerDialog() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		Calendar cal = Calendar.getInstance();

		DatePickerDialog dialog = new DatePickerDialog(CalendarActivity.this,
				new DatePickerDialog.OnDateSetListener() {

					public void onDateSet(DatePicker picker, int year,
							int month, int day) {

						SharedPreferences preference = getSharedPreferences(
								FILE_NAME, MODE_PRIVATE);
						// TODO �����������ꂽ���\�b�h�E�X�^�u
						SharedPreferences.Editor editor = preference.edit();
						editor.putString("DATE", year + "�N" + (month + 1) + "��"
								+ day + "��");
						editor.commit();

						date = preference.getString("DATE", "����܂���");

						datelist += date+"\n";
				        //���C�A�E�g���TextView�C���X�^���X���擾����
				        TextView showDate = (TextView) findViewById(R.id.tv_date);
				        //�������\������
				        showDate.setText(datelist);
				        Log.i("DATE", datelist);
				        databasein();
				        //�������ǉ�����
//				        showDate.append("World!!!");
//				        waitflag = 1;
					}
				}, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
				cal.get(Calendar.DAY_OF_MONTH));
		dialog.show();


	}

	private void databasein() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		helper = new CreateProductHelper(CalendarActivity.this);
		db = helper.getWritableDatabase();		//db file �����

		try {
			String sql = "create table DateSql ("
					+ "_id integer primary key autoincrement,"
					+ "DateSql text not null)";

			db.execSQL(sql);

			message = "�e�[�u�����쐬���܂����I\n";
			Log.i("INFO", message);
		} catch (Exception e) {
			message = "�e�[�u���͍쐬����Ă��܂��I\n";
			Log.e("ERROR", e.toString());
		}

		try {
			db.beginTransaction();

			ContentValues val = new ContentValues();
			val.put("DateSql", date);
			Log.i("INFO", date);

			db.insert("DateSql", null, val);
			db.setTransactionSuccessful();
			db.endTransaction();

			message += "�f�[�^��o�^���܂����I";
		} catch (Exception e) {
			message = "�f�[�^�o�^�Ɏ��s���܂����I";
			Log.e("ERROR", e.toString());
		}

	}

	private void showDialog(String text) {
		AlertDialog.Builder dialog = new AlertDialog.Builder(
				CalendarActivity.this);
		dialog.setTitle("�ۑ�����");
		dialog.setMessage(text);
		dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int whichButton) {
				// TODO �����������ꂽ���\�b�h�E�X�^�u
				CalendarActivity.this.setResult(Activity.RESULT_OK);

			}
		});
		dialog.create();
		dialog.show();
	}

}
