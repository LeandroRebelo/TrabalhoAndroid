package edu.asselvi.leandro_01.views;

import edu.asselvi.leandro_01.R;
import edu.asselvi.leandro_01.enumm.ResultCode;
import edu.asselvi.leandro_01.model.Pessoa;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

public class PessoaCadastroActivity extends Activity {

	private Pessoa pessoa;
	private RadioButton radioMasculino;
	private RadioButton radioFeminino;
	private EditText editID;
	private EditText editNome;
	private EditText editCPF;
	private EditText editEmail;
	private EditText editTelefoneFixo;
	private EditText editTelefoneCelular;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pessoa);
	
		initItensView();
		
		pessoa = (Pessoa) getIntent().getSerializableExtra("pessoa");
		
		if (pessoa != null) {
			editID.setText(pessoa.getId().toString());
			editNome.setText(pessoa.getNome());
			editCPF.setText(pessoa.getCpf());
			editEmail.setText(pessoa.getEmail());
			editTelefoneFixo.setText(pessoa.getTelefoneFixo());
			editTelefoneCelular.setText(pessoa.getTelefoneCelular());
			
			if (pessoa.getSexo() == 'M') {
				radioMasculino.isSelected();
			} else if (pessoa.getSexo() == 'F') {
				radioFeminino.isSelected();
			}
		}
		
	}
	
	@SuppressLint("CutPasteId")
	private void initItensView() {
		editID = (EditText) findViewById(R.id.editTextID);
		editNome = (EditText) findViewById(R.id.editTextNome);
		editCPF = (EditText) findViewById(R.id.editTextCPF);
		editEmail = (EditText) findViewById(R.id.editTextEmail);
		editTelefoneFixo = (EditText) findViewById(R.id.editTextTelFixo);
		editTelefoneCelular = (EditText) findViewById(R.id.editTextTelCel);
		radioMasculino = (RadioButton) findViewById(R.id.radioMasculino);
		radioFeminino = (RadioButton) findViewById(R.id.radioFeminino);
	}
	
	public void salvar(View view) {
		Intent intent = new Intent();
		setResult(ResultCode.OK, intent);
		
		try {
			Integer id = Integer.parseInt(editID.getText().toString());
			String nome = editNome.getText().toString();
			String cpf = editCPF.getText().toString();
			String email = editEmail.getText().toString();
			String telefoneFixo = editTelefoneFixo.getText().toString();
			String telefoneCelular = editTelefoneCelular.getText().toString();
			char sexo = 0;
			
			if (radioMasculino.isChecked()) {
				sexo = 'M';
			} else if (radioFeminino.isChecked()) {
				sexo = 'F';
			}
			
			if (pessoa == null) {
				pessoa = new Pessoa(id, telefoneFixo, telefoneCelular, nome, cpf, email, sexo);
			} else {
				pessoa.setId(id);
				pessoa.setNome(nome);
				pessoa.setCpf(cpf);
				pessoa.setEmail(email);
				pessoa.setTelefoneFixo(telefoneFixo);
				pessoa.setTelefoneCelular(telefoneCelular);
				pessoa.setSexo(sexo);
			}
			intent.putExtra("pessoa", pessoa);
			
		} catch (Exception e) {
			setResult(ResultCode.ERROR);
		}
		finish();
	}
	
	public void cancelar(View view) {
		finish();
	}
}
