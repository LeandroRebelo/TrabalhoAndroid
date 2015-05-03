package edu.asselvi.leandro_01.component;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import edu.asselvi.leandro_01.R;
import edu.asselvi.leandro_01.model.Pessoa;

public class PessoaAdapter extends GenericListAdapter<Pessoa>{

	public PessoaAdapter(List<Pessoa> list, Context context) {
		super(list, context);
		// TODO Auto-generated constructor stub
	}

	@SuppressLint("ViewHolder")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View pessoaAdapter = inflater.inflate(R.layout.line_style, parent, false);
		
		TextView textoPrincipal = (TextView) pessoaAdapter.findViewById(R.id.linestyle_textViewPrimary);
		TextView textoSecundario = (TextView) pessoaAdapter.findViewById(R.id.linestyle_textViewSecond);
		ImageView imagemEstrela = (ImageView) pessoaAdapter.findViewById(R.id.linestyle_imageViewStar);
		
		Pessoa pessoa = getItem(position);
		textoPrincipal.setText(pessoa.getId() + " " + pessoa.getNome());
		textoSecundario.setText(pessoa.getEmail() + " - " + pessoa.getTelefoneFixo() + " - " + pessoa.getTelefoneCelular());
		
		if (pessoa.getSexo() == 'M') {
			imagemEstrela.setImageResource(android.R.drawable.star_big_on);
		} else {
			imagemEstrela.setImageResource(android.R.drawable.star_big_off);
		}
		
		return pessoaAdapter;
	}

}
