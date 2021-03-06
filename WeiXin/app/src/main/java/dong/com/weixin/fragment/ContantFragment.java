package dong.com.weixin.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import dong.com.weixin.R;
import dong.com.weixin.adapter.ContantAdapter;
import dong.com.weixin.bean.Contant;
import dong.com.weixin.ui.BladeView;
import dong.com.weixin.utils.ContantsUtil;
import dong.com.weixin.utils.DialogUtil;
import roboguice.fragment.RoboFragment;
import roboguice.inject.InjectView;

public class ContantFragment extends RoboFragment {

    @InjectView(R.id.cn_listview) private ListView listview;
    @InjectView(R.id.bladeView) private BladeView bladeView;
    @Inject private Context mContext;

    private ImageView icon;
    private TextView name;
    private TextView indicator;
    private ContantAdapter adapter;
    private DialogUtil dialog;
    private List<Contant> contants;
    private String[] strs = {"设置备注及标签"};
    private Map<String,Integer> contantMap = new HashMap<String,Integer>();
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_contant, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mContext = getActivity();
        initView();
    }

    private void initView() {
        dialog = new DialogUtil(strs,mContext);

        contants = new ArrayList<Contant>();
        contants.clear();
        contants = ContantsUtil.getContants();

        adapter = new ContantAdapter(mContext,contants);

        AddHanders();
        listview.setAdapter(adapter);

        list2Map();
        setListener();


    }

    private void setListener() {
        bladeView.setOnItemClickListener(new BladeView.OnItemClickListener() {
            @Override
            public void onItemClick(String s) {
                if (contantMap.containsKey(s)) {
                    int num = contantMap.get(s);
                    listview.setSelection(num);
                }
                if ("↑".equals(s)) {
                    listview.setSelection(0);
                }
                if ("☆".equals(s)) {
                    listview.setSelection(4);
                }
            }
        });

        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i >3){
                    dialog.ShowDialog();
                }
                return false;
            }
        });

        dialog.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                dialog.dialogDimiss();
            }
        });

    }

    private void list2Map() {
        for(int i = 0; i < contants.size(); i++){
            if(i==0){
                contantMap.put(contants.get(i).getFirstLitter(),i+4);
            }else{
                if(!contantMap.containsKey(contants.get(i).getFirstLitter())){
                    contantMap.put(contants.get(i).getFirstLitter(),i+4);
                }

            }
        }
    }

    private void AddHanders() {

        for (int i =0 ; i<4; i++){
            View view = View.inflate(mContext,R.layout.item_list_contant,null);
            indicator = (TextView) view.findViewById(R.id.indicator);
            icon = (ImageView) view.findViewById(R.id.icon);
            name = (TextView) view.findViewById(R.id.name);
            indicator.setVisibility(View.GONE);
            if(i==0){
                icon.setImageResource(R.mipmap.new_friend);
                name.setText("新的朋友");
            }
            if(i==1){
                icon.setImageResource(R.mipmap.group_chat);
                name.setText("群聊");
            }
            if(i==2){
                icon.setImageResource(R.mipmap.biaoqian);
                name.setText("标签");
            }
            if(i==3){
                icon.setImageResource(R.mipmap.gongzonghao);
                name.setText("公众号");
            }
            listview.addHeaderView(view);
        }

    }


}
