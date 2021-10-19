package com.aks.expencetracker.activities;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.aks.expencetracker.R;

public class GetContactsFromPhoneActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_contacts_from_phone);
    }

    @SuppressLint("Range")
    private void getContactList() {
        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);

        if ((cur != null ? cur.getCount() : 0) > 0) {
            while (cur.moveToNext()) {
                String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                if (cur.getInt(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        String phoneNo = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        Log.i("Name: ", name);
                        Log.i("Phone Number: ", phoneNo);
                    }
                    pCur.close();
                }
            }
        }
        if (cur != null) {
            cur.close();
        }
    }

    @SuppressLint("Range")
    public void getWhatsAppNumbers(String contactName) {
        Cursor cursor1 = getContentResolver().query(
                ContactsContract.RawContacts.CONTENT_URI,
                new String[]{ContactsContract.RawContacts._ID},
                ContactsContract.RawContacts.ACCOUNT_TYPE + "= ? AND " + ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME_PRIMARY + " = ?",
                new String[]{"com.whatsapp", contactName},
                null);

        while (cursor1.moveToNext()) {
            String rawContactId = cursor1.getString(cursor1.getColumnIndex(ContactsContract.RawContacts._ID));

            Cursor cursor2 = getContentResolver().query(
                    ContactsContract.Data.CONTENT_URI,
                    new String[]{ContactsContract.Data.DATA3},
                    ContactsContract.Data.MIMETYPE + " = ? AND " + ContactsContract.Data.RAW_CONTACT_ID + " = ? ",
                    new String[]{"vnd.android.cursor.item/vnd.com.whatsapp.profile", rawContactId},
                    null);

            while (cursor2.moveToNext()) {
                String phoneNumber = cursor2.getString(0);

                if (TextUtils.isEmpty(phoneNumber))
                    continue;

                if (phoneNumber.startsWith("Message "))
                    phoneNumber = phoneNumber.replace("Message ", "");

                Log.d("whatsapp", String.format("%s - %s", contactName, phoneNumber));
            }
        }
    }
}