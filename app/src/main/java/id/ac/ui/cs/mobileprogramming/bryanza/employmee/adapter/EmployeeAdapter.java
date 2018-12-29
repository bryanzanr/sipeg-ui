package id.ac.ui.cs.mobileprogramming.bryanza.employmee.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import id.ac.ui.cs.mobileprogramming.bryanza.employmee.R;
import id.ac.ui.cs.mobileprogramming.bryanza.employmee.model.ModelEmployee;

import java.util.ArrayList;
import java.util.List;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.NoteHolder> {

    private Context mContext;
    private List<ModelEmployee> notes;

    public EmployeeAdapter(Context mContext, List<ModelEmployee> notes){
        this.mContext = mContext;
        this.notes = notes;
    }

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext)
                .inflate(R.layout.employee_data, parent, false);
        return new NoteHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
        ModelEmployee currentNote = notes.get(position);
        holder.textViewTitle.setText(currentNote.getName());
        holder.textViewDescription.setText(currentNote.getNim());
        holder.textViewPriority.setText(String.valueOf(currentNote.getGender()));
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void setNotes(List<ModelEmployee> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    class NoteHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewDescription;
        private TextView textViewPriority;

        public NoteHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.promotion_item_title);
            textViewDescription = itemView.findViewById(R.id.promotion_item_special_label);
            textViewPriority = itemView.findViewById(R.id.promotion_item_subtitle);
        }
    }
}