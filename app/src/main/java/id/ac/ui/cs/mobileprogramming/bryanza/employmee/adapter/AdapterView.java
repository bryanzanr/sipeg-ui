package id.ac.ui.cs.mobileprogramming.bryanza.employmee.adapter;

import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import id.ac.ui.cs.mobileprogramming.bryanza.employmee.R;
import id.ac.ui.cs.mobileprogramming.bryanza.employmee.model.ModelEmployee;

public class AdapterView extends ListAdapter<ModelEmployee, AdapterView.NoteHolder> {
    private OnItemClickListener listener;

    public AdapterView() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<ModelEmployee> DIFF_CALLBACK = new DiffUtil.ItemCallback<ModelEmployee>() {
        @Override
        public boolean areItemsTheSame(ModelEmployee oldItem, ModelEmployee newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(ModelEmployee oldItem, ModelEmployee newItem) {
            return oldItem.getName().equals(newItem.getName()) &&
                    oldItem.getNim().equals(newItem.getNim()) &&
                    oldItem.getGender() == newItem.getGender();
        }
    };

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.employee_data, parent, false);
        return new NoteHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
        ModelEmployee currentNote = getItem(position);
        holder.textViewTitle.setText(currentNote.getName());
        holder.textViewDescription.setText(currentNote.getNim());
        holder.textViewPriority.setText(String.valueOf(currentNote.getGender()));
    }

    public ModelEmployee getNoteAt(int position) {
        return getItem(position);
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

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(ModelEmployee note);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}