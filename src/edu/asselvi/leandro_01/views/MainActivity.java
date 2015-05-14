package edu.asselvi.leandro_01.views;

import java.util.ArrayList;

import edu.asselvi.leandro_01.R;
import edu.asselvi.leandro_01.component.PessoaAdapter;
import edu.asselvi.leandro_01.enumm.ResultCode;
import edu.asselvi.leandro_01.model.Pessoa;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {

	private Button button;
	private ListView listView;
	private TextView textView;
	private PessoaAdapter pessoaAdapter;
	private AlertDialog alerta;
	private AlertDialog excluir;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		listView = (ListView) findViewById(R.id.act1_listView1);
		button = (Button) findViewById(R.id.act1_buttonAdd);
		textView = (TextView) findViewById(R.id.act1_textView1);
		
		pessoaAdapter = new PessoaAdapter(new ArrayList<Pessoa>(), getApplicationContext());
		pessoaAdapter.addOrReplaceItem(new Pessoa(100, "000-0000", "000-0000", "Leandro", "000.000.000-07", "leandro@leandro", 'M'));
		pessoaAdapter.addOrReplaceItem(new Pessoa(101, "000-0000", "000-0000", "Bruna", "000.000.000-07", "bruna@bruna", 'F'));
		
		listView.setAdapter(pessoaAdapter);
				
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(getApplicationContext(), PessoaCadastroActivity.class);
				intent.putExtra("pessoa", pessoaAdapter.getItem(position));
				startActivityForResult(intent, 2);
			}
		});
		
		listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
					excluir(pessoaAdapter.getItem(position));
				return true;
			}
		});
		
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				cadastroPessoa(button);
			}
		});
		
		count();
	}
	
	private void count() {
		textView.setText("Contatos Apps, " + listView.getCount() + " encontrados");
	}
	
	public void cadastroPessoa (View view) {
		Intent intent = new Intent(getApplicationContext(), PessoaCadastroActivity.class);
		startActivityForResult(intent, 1);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == ResultCode.OK) {
			Pessoa pessoa = (Pessoa) data.getSerializableExtra("pessoa");
			if (pessoa != null) {
				pessoaAdapter.addOrReplaceItem(pessoa);
			}
			listView.invalidateViews();
			count();
		} else if (resultCode == ResultCode.ERROR) {
			
		}
	}
	
	//Menu superior
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_superior, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.searchoption) {
			Intent intent = new Intent(getApplicationContext(), ContatoActivity.class);
			startActivityForResult(intent, 1);
		} else if (item.getItemId() == R.id.close) {
			close();
		}
		return super.onOptionsItemSelected(item);
	}
	
	//AlertDialog Close
	private void close() {
		AlertDialog.Builder builderClose = new AlertDialog.Builder(this);
		builderClose.setTitle("Sair");
		builderClose.setMessage("Deseja sair?");
		
		builderClose.setNegativeButton("Não", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {				
			}
		});

		builderClose.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				finish();
			}
		});
		
		alerta = builderClose.create();
		alerta.show();
	}
	
	private void excluir(final Pessoa pessoa) {
		AlertDialog.Builder builderExcluir = new AlertDialog.Builder(this);
		builderExcluir.setTitle("Exclusão");
		builderExcluir.setMessage("Deseja excluir");
		
		builderExcluir.setNegativeButton("Não", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
			}
		});
		
		builderExcluir.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				pessoaAdapter.removeItem(pessoa);
				listView.invalidateViews();
				count();
			}
		});
		
		excluir = builderExcluir.create();
		excluir.show();
	}
	
	@Override
	public void onBackPressed() {
		close();
	}
}
