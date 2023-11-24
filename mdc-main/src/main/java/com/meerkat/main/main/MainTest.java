package com.meerkat.main.main;

import com.alibaba.fastjson.JSONObject;
import com.meerkat.common.utils.CommHttpUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 测试
 *
 * @author zhujx
 * @date 2022/06/07 13:41
 */
public class MainTest {


    public static void main(String[] args) {
        String cookie = "DXY_TRACE_ID=MluwzbIKCVsDCfuo6ITzLEo6vKv6ywam; _ga=GA1.2.939273815.1646798417; Hm_lvt_f21c182642df0697ca3ebaf7a82b8fc4=1646798417,1646798988,1646799168,1648458937; CHD_USER_ID=3531481710027989756; DXY_CHD_SESSION=eyJhIjoxMjIxNzgyNDEyLCJ0IjoxNjU0NjY3MDI1LCJuIjoiUm5Rc2ZXIiwiZCI6IntcImF0dHJpYnV0ZXNcIjp7XCJzc29cIjpcImR4eV9odzZwZTg2MlwiLFwidlwiOjAsXCJjaWRcIjozNTM3NjQyODIzNTY0MzI3NzQ0LFwic3VwXCI6MzUzNzY0MjgyMzU2NDMyNzc0NH0sXCJpZFwiOjM1MzE0ODE3MTAwMjc5ODk3NTYsXCJ1c2VybmFtZVwiOlwiZHh5X2h3NnBlODYyXCIsXCJtYXJrc1wiOjE4LFwibW9tXCI6LTF9IiwicyI6ImFkMjA5M2UxNWRiY2YxMTg5ZTMxNDQyYjE0NjhkN2U0YmU4MzhkMzkifQ==";
        String detailUrl = "https://mama.dxy.com/api/partner-gateway/clinic/reserve/treat";
        Map<String, String> header = new HashMap<>();
        header.put("cookie", cookie);
        header.put("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/102.0.0.0 Safari/537.36");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", "3544611891181595815");
        jsonObject.put("writeOffCode", "70371153");
        String result = CommHttpUtils.doPostJson(detailUrl, jsonObject.toJSONString(), header);
        System.out.println(result);

    }
    //public static void main(String[] args) {
    //    String cookie = "DXY_TRACE_ID=Z8PD66JEb5n74o9EysMTcAARA7vg2yML; route_sso=1650939514.674.36299.510544;
    //    JSESSIONID=5855A1F01C0FDBEF13EB2D83A8F92860; dxy_da_cookie-id=01c7b5cff98342916c04f77dd600b04c1650939513859;
    //    _da_sid=01C7B5CFF98342916C04F77DD600B04C1650939513859; _da_pid=b3c11487e9efad3afc7715e5b4906ef31650939513863;
    //    route_dq=441c1db7263d1b4f7a2578e1ba3e34ff; route_dq=441c1db7263d1b4f7a2578e1ba3e34ff;
    //    CLASS_CASTGC
    //    =9f69074e6f9ed1a258c4151e5bdbb571e07a5fe80e1fdc989d5fb9e2171763ac2bbb4b898d594b4010283a529a73290108f50c225dc9df62b42a8d89e6b98f3ab2b9cdbb67b80ede64a52df2d8defb91537c3a35afe74f1d71b2742e09df83cf9e00057faab8b65cd8241dd428fc23107a0feb18b2f540e82c0c21b5060a10a08fe15dc948cc39390b324740d525fd59debc8e1087a0ce65edeff9918546ca668c128ecfde1d538193fdbebb8807ec7adce522a6073576c3a42e04737ef0c57de6228e47f1b17e15d953dafddaa61b4d10f8cf9d209625c3def082be50ad86edc29bd39769946450a14ced8a2b8856f9e2ad5eab49074a3fcdeb6da1cffd6564; JUTE_BBS_DATA=64061c08f3f6c237fa3b70862389fe08cca32a7c0a30edf6c25c5433097cc9dd61f8ef2d52d509b5b574dca07fae8d7a20cd602f3131031eda2e880c1402bb00b1be8aab58f68e41323df0a8891958b7; CASTGC=TGT-18890-AdKlvw4bbk57L1SjqfuwXezLmLLgU7RaEpD-50; CHD_USER_ID=3522351232327064438; DXY_CHD_SESSION=eyJhIjoxMjIxNzgyNDEyLCJ0IjoxNjU0NjY1NTE2LCJuIjoiSTFFU01iIiwiZCI6IntcImF0dHJpYnV0ZXNcIjp7XCJzc29cIjpcImR4eV9jMG50b21oN1wiLFwidlwiOjAsXCJjaWRcIjozNTM3NjQyODIzNTY0MzI3NzQ0LFwic3VwXCI6MzUzNzY0MjgyMzU2NDMyNzc0NH0sXCJpZFwiOjM1MjIzNTEyMzIzMjcwNjQ0MzgsXCJ1c2VybmFtZVwiOlwiZHh5X2MwbnRvbWg3XCIsXCJtYXJrc1wiOjE4LFwibW9tXCI6LTF9IiwicyI6ImNiMzZlMjQ3OWQyMGViOWE4YzE2YTAyMjhmOTc1ODBjMzYyMzVmM2IifQ==";
    //    String detailUrl = "https://mama.dxy.com/api/partner-gateway/clinic/reserve/treat/confirm";
    //    Map<String, String> header = new HashMap<>();
    //    header.put("cookie", cookie);
    //    HashMap<String, String> param = new HashMap<>();
    //    param.put("id", "3544505436122219803");
    //    param.put("writeOffCode", "64183010");
    //    String result = CommHttpUtils.doGet(detailUrl, param, header);
    //    System.out.println(result);
    //
    //}
}
