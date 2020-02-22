package com.pixelswordgames.fgdz.BookView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pixelswordgames.fgdz.POJO.Task;
import com.pixelswordgames.fgdz.R;

import java.util.ArrayList;
import java.util.List;

public class TVAdapter extends RecyclerView.Adapter<TVAdapter.TaskViewHolder> {

    public static class TaskViewHolder extends RecyclerView.ViewHolder{

        TextView nameView;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);

            nameView = ((TextView)itemView.findViewById(R.id.tNameView));
        }

        public void setTask(Task task){
            nameView.setText(task.getName());
        }
    }



    public TVAdapter(RecyclerView recyclerView){
        tasks = new ArrayList<>();
        loading = false;

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int lastVisibleItem = ((GridLayoutManager)recyclerView.getLayoutManager()).findLastVisibleItemPosition();
                if(!loading && tasks.size() <= (lastVisibleItem+1)){
                    if(onMoreLoadListener != null)
                        onMoreLoadListener.onLoadMore(lastVisibleItem);
                    loading = true;
                }
            }
        });
    }
    private OnMoreLoadListener onMoreLoadListener;
    private List<Task> tasks;
    private boolean loading;

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item,parent,false);
        TaskViewHolder tvh = new TaskViewHolder(view);
        return tvh;
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        holder.setTask(tasks.get(position));
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public void setTasks(List<Task> tasks){
        this.tasks = tasks;
        notifyDataSetChanged();
    }

    public Task getTask(int position){
        return  tasks.get(position);
    }

    public void setLoading(boolean loading) {
        this.loading = loading;
    }

    public interface OnMoreLoadListener{
        void onLoadMore(int lastItem);
    }

    public List<Task> getTasks(){
        return tasks;
    }

    public void setOnMoreLoadListener(OnMoreLoadListener onMoreLoadListener) {
        this.onMoreLoadListener = onMoreLoadListener;
    }

    public void addTask(Task task){
        tasks.add(task);
        notifyItemInserted(tasks.size()-1);
    }
}
