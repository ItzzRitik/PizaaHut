package aditya.ritik.dbms_project;
import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CustomAdapter extends BaseAdapter {
    String[] result;
    String[] quantity;
    String[] price;
    menu context;
    LayoutInflater inflater=null;
    public CustomAdapter(menu mainActivity, String[] search,String[] qu,String[] pr) {
        result=search;price=pr;quantity=qu;
        context=mainActivity;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {return result.length;}
    @Override
    public Object getItem(int position) {return position;}
    @Override
    public long getItemId(int position) {return position;}
    public class Holder {TextView item,quan,price;}
    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.items, null);
        holder.item=(TextView) rowView.findViewById(R.id.item_name);holder.item.setText(result[position]);
        holder.quan=(TextView) rowView.findViewById(R.id.quan);holder.quan.setText(quantity[position]);
        holder.price=(TextView) rowView.findViewById(R.id.price);
        holder.price.setText("₹"+Integer.parseInt(price[position].substring(1))*Integer.parseInt(quantity[position]));
        return rowView;
    }
}