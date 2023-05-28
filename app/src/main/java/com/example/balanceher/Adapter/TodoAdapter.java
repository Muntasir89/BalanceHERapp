package com.example.balanceher.Adapter;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.balanceher.Interface.NoteDeleteInterface;
import com.example.balanceher.MVVM.Model.Note;
import com.example.balanceher.R;
import com.example.balanceher.databinding.TodoCardBinding;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class TodoAdapter extends ListAdapter<Note, TodoAdapter.ItemViewHolder> {
    SimpleDateFormat timeFormat;

    NoteDeleteInterface noteDeleteInterface;
    public TodoAdapter(Context context) {
        super(DiffCallback);
        noteDeleteInterface = (NoteDeleteInterface)  context;
        //Initialization of timeFormat to convert millisec -> TimeFormat
        timeFormat = new SimpleDateFormat("hh:mm a");
        timeFormat.setTimeZone(TimeZone.getDefault());
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        TodoCardBinding binding = TodoCardBinding.inflate(inflater, parent, false);
        return new ItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position){
        Note note = getItem(position);
        holder.bind(note);
    }

    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {
        TodoCardBinding binding;
        PopupMenu moreMenu;
        public ItemViewHolder(@NonNull TodoCardBinding bnding) {
            super(bnding.getRoot());
            binding = bnding;
            //Initialization of popup menu
            moreMenu = new PopupMenu(bnding.moreBtn.getContext(), bnding.moreBtn);
            moreMenu.inflate(R.menu.more_menu);
            moreMenu.setOnMenuItemClickListener(this);
        }
        void bind(Note note){
            binding.titleTV.setText(note.getTitle());
            binding.detailsTV.setText(note.getDetails());
            binding.timeTV.setText(timeFormat.format(new Date(note.getMilliseconds())));
            //clickListener
            binding.moreBtn.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Log.d(TAG, "onclicked "+getAdapterPosition());
            if(v.getId()==R.id.moreBtn)
                moreMenu.show();
        }

        @Override
        public boolean onMenuItemClick(MenuItem item){
            if(item.getItemId()==R.id.edit_item){
                Toast.makeText(itemView.getContext(), "clicked on :edit", Toast.LENGTH_SHORT).show();
                return true;
            }
            else if(item.getItemId()==R.id.delete_item){
                noteDeleteInterface.getDeleteNote(getNote(getAdapterPosition()));  // Getting+passing note for deletion
                Toast.makeText(itemView.getContext(), "clicked on :delete", Toast.LENGTH_SHORT).show();
                return true;
            }
            return false;
        }
        public Note getNote(int position){
            return getItem(position);
        }
    }

    private static final DiffUtil.ItemCallback<Note>DiffCallback = new DiffUtil.ItemCallback<Note>() {
        @Override
        public boolean areItemsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return false;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return (oldItem.getTitle().equals(newItem.getTitle()) || oldItem.getDetails().equals(newItem.getDetails()));
        }
    };
}