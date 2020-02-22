package com.pixelswordgames.fgdz.BookView;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pixelswordgames.fgdz.DecisionView.DecisionActivity;
import com.pixelswordgames.fgdz.POJO.Task;
import com.pixelswordgames.fgdz.POJO.TaskGroup;
import com.pixelswordgames.fgdz.R;
import com.pixelswordgames.fgdz.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class GVAdapter extends RecyclerView.Adapter<GVAdapter.GroupViewHolder> {
    private List<TaskGroup> groups;
    private Context context;
    private boolean showTasks;

    public GVAdapter(Context context){
        this.context = context;
        groups = new ArrayList<>();
        showTasks = false;
    }

    @NonNull
    @Override
    public GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_item,parent,false);
        GroupViewHolder tvh = new GroupViewHolder(view);
        return tvh;
    }

    @Override
    public void onBindViewHolder(@NonNull GroupViewHolder holder, int position) {
        holder.setContent(groups.get(position), context, showTasks);
    }

    @Override
    public int getItemCount() {
        return groups.size();
    }

    public static class GroupViewHolder extends RecyclerView.ViewHolder{

        private TextView nameView;
        private RecyclerView tasksListView;
        private TVAdapter adapter;
        private ImageView openBtn;

        public GroupViewHolder(@NonNull View itemView) {
            super(itemView);

            openBtn = ((ImageView)itemView.findViewById(R.id.grOpen));
            nameView = ((TextView)itemView.findViewById(R.id.grName));
            tasksListView = ((RecyclerView)itemView.findViewById(R.id.grGridView));

            View.OnClickListener clickListener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(tasksListView.getVisibility() == View.VISIBLE)
                        setListGone();
                    else
                        setListVisible();
                }
            };

            openBtn.setOnClickListener(clickListener);
            nameView.setOnClickListener(clickListener);

            tasksListView.addOnItemTouchListener(new RecyclerItemClickListener(itemView.getContext(), tasksListView, new RecyclerItemClickListener.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Task task = adapter.getTask(position);
                    Intent intent = new Intent(view.getContext(), DecisionActivity.class);
                    Pair<String[], String[]> data = getArrays(adapter.getTasks());

                    intent.putExtra("curDec", position);
                    intent.putExtra("decNames",data.first);
                    intent.putExtra("decUrls",data.second);
                    view.getContext().startActivity(intent);
                }

                @Override
                public void onLongItemClick(View view, int position) {

                }
            }));
        }

        private Pair<String[],String[]> getArrays(List<Task> tasks){
            String[] names = new String[tasks.size()];
            String[] urls = new String[tasks.size()];

            for(int i =0; i < tasks.size(); ++i){
                names[i] = tasks.get(i).getName();
                urls[i] = tasks.get(i).getUrl();
            }

            return new Pair<>(names, urls);
        }

        public void setContent(TaskGroup group, Context context, boolean showTasks ){
            nameView.setText(group.getName());
            tasksListView.setLayoutManager(new GridLayoutManager(context,5));
            adapter = new TVAdapter(tasksListView);
            tasksListView.setAdapter(adapter);

            adapter.setTasks(group.getTasks());

            tasksListView.setHasFixedSize(true);

            if(showTasks)
                setListVisible();
        }

        private void setListVisible( ){
            tasksListView.setVisibility(View.VISIBLE);
            openBtn.setImageResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
        }

        private void setListGone(){
            tasksListView.setVisibility(View.GONE);
            openBtn.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
        }

    }

    void setGroups(List<TaskGroup> groups){
        this.groups = groups;
        notifyDataSetChanged();
    }

    void setShowTasks(boolean showTasks){
        this.showTasks = showTasks;
    }
}
