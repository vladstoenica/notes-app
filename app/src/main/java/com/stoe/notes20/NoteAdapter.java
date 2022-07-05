package com.stoe.notes20;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteHolder> { //  cu <> conectam noteadapter la noteholder

    private List<Note> notes = new ArrayList<>();
    private OnItemClickListener listener;      //pt functia setOnItemCLickListener, folosit ca sa call-uim onItemClick



    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_item, parent, false);

        return new NoteHolder(view);
    }

    //transferam data from each java obj to the noteholder
    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
        Note currentNote = notes.get(position);
        holder.textViewTitle.setText(currentNote.getTitle());
        holder.textViewDescription.setText(currentNote.getDescription());
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void setNotes(List<Note> notes){
        this.notes = notes;
        notifyDataSetChanged();  //will alert the adapter if there is a change in the DB
    }

    public Note getNotes(int position){  //folosita pt onSwiped (delete)
        return notes.get(position);
    }

    //e primul pas dupa ce am dat extends RecyclerView.Adapter, abia dupa fac implement methods pt adapter
    class NoteHolder extends RecyclerView.ViewHolder{
        TextView textViewTitle;
        TextView textViewDescription;

        public NoteHolder(@NonNull View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewDescription = itemView.findViewById(R.id.textViewDescription);

            //codul de sub e folosit pt clickurile pe recycler
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(listener != null && position != RecyclerView.NO_POSITION){
                        listener.onItemClick(notes.get(position));
                    }
                }
            });

        }
    }

    //adaugam un onClickListener pt Recycler cards (pt update note)
    public interface OnItemClickListener{
        void onItemClick(Note note);
    }

    //interfata de sus si asta de jos sunt legate. sunt folosite pt listener.onItemClick(notes.get(position))
    public void setOnItemCLickListener(OnItemClickListener listener){   //recycler view nu are asa ceva asa ca il cream noi
        this.listener = listener;
    }

}
