package aditya.ritik.dbms_project;

        import android.support.v7.widget.RecyclerView;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.CheckBox;
        import android.widget.ImageView;
        import android.widget.Spinner;
        import android.widget.TextView;

        import java.util.ArrayList;
        import java.util.List;

public class PizzaAdapter extends RecyclerView.Adapter<PizzaAdapter.MyViewHolder> {

    private menu mContext;
    public List<pizza> pizzalist;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, count;
        public ImageView thumbnail;
        public CheckBox check;
        public Spinner quantity;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            count = (TextView) view.findViewById(R.id.count);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            check = (CheckBox) view.findViewById(R.id.check);
            quantity = (Spinner) view.findViewById(R.id.quantity);
        }
    }
    public PizzaAdapter (menu mContext, List<pizza> pizzalist) {
        this.mContext = mContext;
        this.pizzalist = pizzalist;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.pizza_card, parent, false);
        return new MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        pizza pizz = pizzalist.get(position);
        holder.title.setText(pizz.getName());
        holder.count.setText("₹"+pizz.getPrice());
        holder.thumbnail.setImageResource(pizz.getThumbnail());

        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.check.performClick();
            }
        });
        holder.count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.check.performClick();
            }
        });
        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {holder.check.performClick();
            }
        });
        holder.check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                addItem(holder);
            }
        });

        List<String> quan = new ArrayList<String>();
        for(int i=1;i<=50;i++) {quan.add(i+"");}
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, quan);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.quantity.setAdapter(dataAdapter);
    }
    @Override
    public int getItemCount() {
        return pizzalist.size();
    }
    public void addItem(final MyViewHolder holder)
    {
        mContext.Iname.add(holder.title.getText().toString());
        mContext.Iprice.add(holder.count.getText().toString());
        mContext.Iquan.add(holder.quantity.getSelectedItem().toString());
    }
    public void deleteItem(final MyViewHolder holder)
    {
        mContext.Iname.remove(holder.title.getText().toString());
        mContext.Iprice.remove(holder.count.getText().toString());
        mContext.Iquan.remove(holder.quantity.getSelectedItem().toString());
    }
}