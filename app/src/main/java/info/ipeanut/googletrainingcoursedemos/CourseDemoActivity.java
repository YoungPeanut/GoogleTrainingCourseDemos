package info.ipeanut.googletrainingcoursedemos;

import android.app.ListActivity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourseDemoActivity extends ListActivity {
    //intent-filter
    public static final String MYCATEGORY = "info.ipeanut.googletrainingcoursedemos.activities";
    //上一级目录
    public static final String KEY_PREFIX = "info.ipeanut.googletrainingcoursedemos.key.prefix";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String prefix = getIntent().getStringExtra(KEY_PREFIX);
        if (null == prefix) {
            prefix = "";
        }

        setListAdapter(new SimpleAdapter(this, getData(prefix),android.R.layout.simple_list_item_1,
                new String[]{"title"}, new int[]{android.R.id.text1}));
        getListView().setTextFilterEnabled(true);

    }

    protected List<Map<String, Object>> getData(String prefix) {
        List<Map<String, Object>> myData = new ArrayList<>();
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(MYCATEGORY);
        PackageManager pm = getPackageManager();
        List<ResolveInfo> infos = pm.queryIntentActivities(intent, 0);
        if (null == infos || infos.size() < 1) {
            return myData;
        }

        //当前的路径
        String[] prefixPath;
        String prefixWithSlash = prefix;
        if (TextUtils.isEmpty(prefix)) {
            prefixPath = null;
        } else {
            prefixPath = prefix.split("/");
            prefixWithSlash = prefix + "/";
        }
        Map<String, Boolean> entries = new HashMap<String, Boolean>();

        for (int i = 0; i < infos.size(); i++) {
            ResolveInfo info = infos.get(i);
            CharSequence label = info.loadLabel(pm);
            String activitylabel = label == null ? info.activityInfo.name : label.toString();

            //空字符串 或
            if (prefixWithSlash.length() == 0 || activitylabel.startsWith(prefixWithSlash)) {
                String[] labelPath = activitylabel.split("/");
                String nextpath = prefixPath == null ? labelPath[0] : labelPath[prefixPath.length];

                //确认一下是否是倒数第二级 全路径长度－1 =＝ 倒数第二的路径长度
                if ((labelPath.length - 1) == (prefixPath != null ? prefixPath.length : 0)) {
                    addItem(myData, nextpath, activityIntent(
                            info.activityInfo.applicationInfo.packageName,
                            info.activityInfo.name));
                } else {//不是最后一级
                    //这样一来，每个／／之间的路径名不能重复了
                    if (entries.get(nextpath) == null) {
                        addItem(myData, nextpath, browseIntent(prefix.equals("") ? nextpath : prefix + "/" + nextpath));
                        entries.put(nextpath, true);
                    }
                }
            }
        }
        Collections.sort(myData, sDisplayNameComparator);

        return myData;
    }

    private final static Comparator<Map<String, Object>> sDisplayNameComparator =
            new Comparator<Map<String, Object>>() {
                //字符串比较类
                private final Collator collator = Collator.getInstance();

                @Override
                public int compare(Map<String, Object> map1, Map<String, Object> map2) {
                    return collator.compare(map1.get("title"), map2.get("title"));
                }
            };

    /**
     * 下一级还是MainActivity，只是路径不一样
     *
     * @param path
     * @return
     */
    protected Intent browseIntent(String path) {
        Intent result = new Intent();
        result.setClass(this, CourseDemoActivity.class);
        result.putExtra(KEY_PREFIX, path);
        return result;
    }

    /**
     * 下一级不是MainActivity列 ，是具体类
     *
     * @param pkg
     * @param componentName
     * @return
     */
    protected Intent activityIntent(String pkg, String componentName) {
        Intent result = new Intent();
        result.setClassName(pkg, componentName);
        return result;
    }

    protected void addItem(List<Map<String, Object>> data, String name, Intent intent) {
        Map<String, Object> temp = new HashMap<String, Object>();
        temp.put("title", name);
        temp.put("intent", intent);
        data.add(temp);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Map<String, Object> map = (Map<String, Object>)l.getItemAtPosition(position);

        Intent intent = (Intent) map.get("intent");
        startActivity(intent);
    }
}
