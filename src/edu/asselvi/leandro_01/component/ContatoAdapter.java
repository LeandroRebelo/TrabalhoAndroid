package edu.asselvi.leandro_01.component;

import java.util.List;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import edu.asselvi.leandro_01.R;
import edu.asselvi.leandro_01.model.Contato;

public class ContatoAdapter extends GenericListAdapter<Contato>{

	public ContatoAdapter(List<Contato> list, Context context) {
		super(list, context);
		// TODO Auto-generated constructor stub
	}

	@SuppressLint("ViewHolder")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View contatoAdapter = inflater.inflate(R.layout.line_style, parent, false);
		
		TextView textoPrincipal = (TextView) contatoAdapter.findViewById(R.id.linestyle_textViewPrimary);
		TextView textoSecundario = (TextView) contatoAdapter.findViewById(R.id.linestyle_textViewSecond);
		
		Contato contato = getItem(position);
		textoPrincipal.setText(contato.getId() + " " + contato.getNome());
		textoSecundario.setText(contato.getEmail() + " - " + contato.getTelefoneFixo() + " - " + contato.getTelefoneCelular());
		
		return contatoAdapter;
	}

}
