package com.tdw;

import static org.junit.Assert.assertEquals;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.junit.*;
import static org.mockito.Mockito.*;
import com.google.gson.Gson;

import java.util.*;

public class NamaraTest {
    Namara subject;
    String dataset;
    String version;
    HashMap<String, String> options;

    @Before
    public void setUp() {
        subject = new Namara("myapikey");
        dataset = "18b854e3-66bd-4a00-afba-8eabfc54f524";
        version = "en-2";
        options = new HashMap<String, String>();
    }

    @Test
    public void getBasePath() {
        assertEquals(subject.getBasePath(dataset, version), "https://api.namara.io/v0/data_sets/18b854e3-66bd-4a00-afba-8eabfc54f524/data/en-2");
    }

    @Test
    public void getPath() {
        assertEquals(subject.getPath(dataset, version, options), "https://api.namara.io/v0/data_sets/18b854e3-66bd-4a00-afba-8eabfc54f524/data/en-2?api_key=myapikey&");
    }

    @Test
    public void getPathLimit() {
        options.put("limit", "1");
        assertEquals(subject.getPath(dataset, version, options), "https://api.namara.io/v0/data_sets/18b854e3-66bd-4a00-afba-8eabfc54f524/data/en-2?api_key=myapikey&limit=1&");
    }

    @Test
    public void getPathOffset() {
        options.put("offset", "1");
        assertEquals(subject.getPath(dataset, version, options), "https://api.namara.io/v0/data_sets/18b854e3-66bd-4a00-afba-8eabfc54f524/data/en-2?api_key=myapikey&offset=1&");
    }

    @Test
    public void getPathSelect() {
        options.put("select", "facility_code");
        assertEquals(subject.getPath(dataset, version, options), "https://api.namara.io/v0/data_sets/18b854e3-66bd-4a00-afba-8eabfc54f524/data/en-2?api_key=myapikey&select=facility_code&");
    }

    @Test
    public void getPathSum() {
        options.put("operation", "sum(facility_code)");
        assertEquals(subject.getPath(dataset, version, options), "https://api.namara.io/v0/data_sets/18b854e3-66bd-4a00-afba-8eabfc54f524/data/en-2/aggregation?api_key=myapikey&operation=sum%28facility_code%29&");
    }

    @Test
    public void getPathAvg() {
        options.put("operation", "avg(facility_code)");
        assertEquals(subject.getPath(dataset, version, options), "https://api.namara.io/v0/data_sets/18b854e3-66bd-4a00-afba-8eabfc54f524/data/en-2/aggregation?api_key=myapikey&operation=avg%28facility_code%29&");
    }

    @Test
    public void getPathMin() {
        options.put("operation", "min(facility_code)");
        assertEquals(subject.getPath(dataset, version, options), "https://api.namara.io/v0/data_sets/18b854e3-66bd-4a00-afba-8eabfc54f524/data/en-2/aggregation?api_key=myapikey&operation=min%28facility_code%29&");
    }

    @Test
    public void testPathWhere() {
        options.put("where", "facility_code>1000");
        assertEquals(subject.getPath(dataset, version, options), "https://api.namara.io/v0/data_sets/18b854e3-66bd-4a00-afba-8eabfc54f524/data/en-2?api_key=myapikey&where=facility_code%3E1000&");
    }

    @Test
    public void testGetCount() {
        options.put("operation", "count(*)");
        Namara namaraMock = mock(Namara.class);
        JsonObject obj = new Gson().fromJson("{result: 129}", JsonObject.class);
        when(namaraMock.<JsonObject>get(dataset, version, options)).thenReturn(obj);
        assertEquals(namaraMock.<JsonObject>get(dataset, version, options).get("result").getAsInt(), 129);
    }

    @Test
    public void testGetJsonSelectCustomClass() {
        options.put("select", "facility_code");
        Namara namaraMock = mock(Namara.class);
        when(namaraMock.<JsonArray>getJson(dataset, version, options)).thenReturn("[{'facility_code': 1000}]");
        String json = namaraMock.getJson(dataset, version, options);
        int facilityCode = Namara.fromJsonToList(json, Facility.class).get(0).facility_code;

        assertEquals(facilityCode, 1000);
    }

    @Test
    public void testGetJsonSelect() {
        options.put("select", "facility_code");
        Namara namaraMock = mock(Namara.class);
        when(namaraMock.<JsonArray>getJson(dataset, version, options)).thenReturn("[{'facility_code': 1000}]");
        String json = namaraMock.getJson(dataset, version, options);
        int facilityCode = Namara.fromJsonToList(json, JsonObject.class).get(0).get("facility_code").getAsInt();

        assertEquals(facilityCode, 1000);
    }

    @Test
    public void testFromJsonToList() {
        options.put("select", "facility_code");
        List<Facility> list = Namara.fromJsonToList("[{'facility_code': 1000}]", Facility.class);

        assertEquals(list.get(0).facility_code, 1000);
    }
}
