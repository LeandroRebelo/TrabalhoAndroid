package edu.asselvi.leandro_01.views;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Contacts;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import edu.asselvi.leandro_01.R;
import edu.asselvi.leandro_01.component.ContatoAdapter;
import edu.asselvi.leandro_01.enumm.ResultCode;
import edu.asselvi.leandro_01.model.Contato;
import edu.asselvi.leandro_01.model.Pessoa;

public class ContatoActivity extends Activity {
	
	private ListView listView;
	private ContatoAdapter contatoAdapter;
	private Contato contatoSelecionado;
	private View ultimoSelecionado;
	private Pessoa pessoa;
	
	private static List<Integer> idsSalvos;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contato);
			
		if (idsSalvos == null) {
			idsSalvos = new ArrayList<Integer>();
		}
		pessoa = (Pessoa) getIntent().getSerializableExtra("pessoa");
		listView = (ListView) findViewById(R.id.act3_listViewContato);
		contatoAdapter = new ContatoAdapter(new ArrayList<Contato>(), getApplicationContext());
		
		carregaContatos();
		
		listView.setAdapter(contatoAdapter);
		
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if (ultimoSelecionado == null) {
					view.setBackgroundColor(Color.GRAY);
					ultimoSelecionado = view;
				} else if (ultimoSelecionado != view) {
					ultimoSelecionado.setBackgroundColor(Color.TRANSPARENT);
					view.setBackgroundColor(Color.GRAY);
					ultimoSelecionado = view;
				}
				contatoSelecionado = contatoAdapter.getItem(position);
			}
		});
		
	}
	
	public void salvar(View view) {
		Intent intent = new Intent();
		setResult(ResultCode.OK, intent);
		
		try {
			Integer id = Integer.parseInt(contatoSelecionado.getId());
			String nome = contatoSelecionado.getNome();
			String telefoneFixo = contatoSelecionado.getTelefoneFixo();
			String telefoneCelular = contatoSelecionado.getTelefoneCelular();
			String email = contatoSelecionado.getEmail();
			String cpf = "000.000.000-00";
			char sexo = 'A';
			
			if (pessoa == null) {
				pessoa = new Pessoa(id, telefoneFixo, telefoneCelular, nome, cpf, email, sexo);
			} else {
				pessoa.setId(id);
				pessoa.setNome(nome);
				pessoa.setTelefoneFixo(telefoneFixo);
				pessoa.setTelefoneCelular(telefoneCelular);
				pessoa.setEmail(email);
				pessoa.setCpf(cpf);
				pessoa.setSexo(sexo);
			}
			idsSalvos.add(id);
			intent.putExtra("pessoa", pessoa);
			
		} catch (Exception e) {
			setResult(ResultCode.ERROR);
		}
		finish();
	}
	
	public void carregaContatos () {
		ContentResolver contentResolver = getContentResolver();
		
		StringBuilder sb = new StringBuilder();
		sb.append(Contacts._ID).append(" NOT IN (");
		int index = 1;
		for (Integer id : idsSalvos) {
			if (idsSalvos.size() == index) {
				sb.append(id);	
			} else {
				sb.append(id).append(", ");
			}
			index++;
		}
		sb.append(")");
		
		Cursor cursor = contentResolver.query(Contacts.CONTENT_URI, null, sb.toString(), null, null);
		
		while (cursor.moveToNext()) {
			Contato contato = new Contato(null, null, null, null, null);
			
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
			contatoAdapter.addOrReplaceItem(contato);					
		}
		cursor.close();
	}
	
	public void cancelar(View view) {
		finish();
	}

}
