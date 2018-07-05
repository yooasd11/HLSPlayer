package com.example.miles.hlsplayer;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatFragment extends Fragment {
    private RecyclerView chatRecycler;
    private ChatAdapter chatAdapter;
    private EditText messageEdit;
    private Button sendButton;
    private final ArrayList<String> messages = new ArrayList();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.chat_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        chatRecycler = view.findViewById(R.id.chat_recycler);
        chatRecycler.setLayoutManager(new LinearLayoutManager(view.getContext()));
        chatAdapter = new ChatAdapter();
        chatRecycler.setAdapter(chatAdapter);
        messageEdit = view.findViewById(R.id.message_edit);
        sendButton = view.findViewById(R.id.send_button);

        sendButton.setOnClickListener(v -> {
            String message = messageEdit.getText().toString();
            if (message.isEmpty()) {
                return;
            }
            chatAdapter.addMessage(message);
            messageEdit.setText("");
            chatRecycler.scrollToPosition(messages.size() - 1);
        });
    }


    private class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            TextView view = (TextView) LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.setMessage(messages.get(position));
        }

        @Override
        public int getItemCount() {
            return messages.size();
        }

        public void addMessage(String message) {
            messages.add(message);
            notifyItemInserted(messages.size() - 1);
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            private TextView textView;

            private ViewHolder(TextView itemView) {
                super(itemView);
                textView = itemView;
            }

            private void setMessage(String message) {
                textView.setText(message);
            }
        }
    }
}
