package edu.asselvi.leandro_01.views;

import java.util.ArrayList;

import edu.asselvi.leandro_01.R;
import edu.asselvi.leandro_01.component.ContatoAdapter;
import edu.asselvi.leandro_01.model.Contato;
import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Contacts;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class ContatoActivity extends Activity {
	
	private ListView listView;
	private ContatoAdapter contatoAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contato);
		
		carregaContatos();
		
		listView = (ListView) findViewById(R.id.act3_listViewContato);
		contatoAdapter = new ContatoAdapter(new ArrayList<Contato>(), getApplicationContext());
		listView.setAdapter(contatoAdapter);
		
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}
	
	public void carregaContatos () {
		ContentResolver contentResolver = getContentResolver();
		Cursor cursor = contentResolver.query(Contacts.CONTENT_URI, null, null, null, null);
		Contato contato = new Contato(null, null, null, null, null);
		
		while (cursor.moveToNext()) {
					
			contato.setId(cursor.getString(cursor.getColumnIndex(Contacts._ID)));
			contato.setNome(cursor.getString(cursor.getColumnIndex(Contacts.DISPLAY_NAME)));
			
			Cursor cursorEmail = contentResolver.query(Email.CONTENT_URI, null, Email.CONTACT_ID+ " = "+ contato.getId(), null, null);
			while (cursorEmail.moveToNext()) {
				contato.setEmail(cursorEmail.getString(cursorEmail.getColumnIndex(Email.ADDRESS)));
			}
			
			Cursor cursorTelefone = contentResolver.query( Phone.CONTENT_URI, null, Phone.CONTACT_ID+ " = "+ contato.getId(), null, null);
			while (cursorTelefone.moveToNext()) {
				int type = cursorTelefone.getInt(cursorTelefone.getColumnIndex(Phone.TYPE));
				switch (type) {
				case Phone.TYPE_HOME:
					contato.setTelefoneFixo(cursorTelefone.getString(cursorTelefone.getColumnIndex(Phone.NUMBER)));
					break;
				case Phone.TYPE_MOBILE:
					contato.setTelefoneCelular(cursorTelefone.getString(cursorTelefone.getColumnIndex(Phone.NUMBER)));
					break;
				default:
					break;
				}
			}		
		}
		//cursor.close();
	}
	
	public void cancelar(View view) {
		finish();
	}

}
