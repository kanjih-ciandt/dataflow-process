package com.kanjih.dataflow.to;



import org.apache.beam.sdk.coders.AvroCoder;
import org.apache.beam.sdk.coders.DefaultCoder;
import org.json.JSONObject;

@DefaultCoder(AvroCoder.class)
public class ObjectToSaveInfo {
  
  
  public enum Field {
    FIELD1("field1"), 
    FIELD2("field2"), 
    FIELD3("field3"), 
    FIELD4("field4"), 
    FIELD5("field5"), 
    FIELD6("field6"), 
    FIELD7("field7"), 
    FIELD8("field8"),
    FIELD9("field9"), 
    FIELD10("field10"), 
    FIELD11("field11"), 
    FIELD12("field12"), 
    FIELD13("field13"),
    FIELD14("field14");
    
    private final String name;       

    private Field(String s) {
        name = s;
    }
    public String toString() {
       return this.name;
    }
  }

  private String field1;
  private String field2;
  private String field3;
  private String field4;
  private String field5;
  private String field6;
  private String field7;
  private String field8;
  private String field9;
  private String field10;
  private String field11;
  private String field12;
  private String field13;
  private String field14;

  public ObjectToSaveInfo(JSONObject json) {
    field1 = json.getString(Field.FIELD1.toString());
    field2 = json.getString(Field.FIELD2.toString());
    field3 = json.getString(Field.FIELD3.toString());
    field4 = json.getString(Field.FIELD4.toString());
    field5 = json.getString(Field.FIELD5.toString());
    field6 = json.getString(Field.FIELD6.toString());
    field7 = json.getString(Field.FIELD7.toString());
    field8 = json.getString(Field.FIELD8.toString());
    field9 = json.getString(Field.FIELD9.toString());
    field10 = json.getString(Field.FIELD10.toString());
    field11 = json.getString(Field.FIELD11.toString());
    field12 =  ((Boolean) json.getBoolean(Field.FIELD12.toString())).toString();
    field13 = json.getString(Field.FIELD13.toString());
    field14 = json.getString(Field.FIELD14.toString());
    this.checkNulls();
  }
  
  private void checkNulls(){
    field1 = field1 != null ? field1 : "null";
    field2 = field2 != null ? field2 : "null";
    field3 = field3 != null ? field3 : "null";
    field4 = field4 != null ? field4 : "null";
    field5 = field5 != null ? field5 : "null";
    field6 = field6 != null  ? field6 : "null";
    field7 = field7 != null ? field7 : "null";
    field8 = field8 != null ? field8 : "null";
    field9 = field9 != null ? field9 : "null";
    field10 = field10 != null ? field10 : "null";
    field11 = field11 != null ? field11 : "null";
    field12 = field12 != null ? field12 : "null";
    field13 = field13 != null ? field13 : "null";
    field14 = field14 != null ? field14 : "null";
  }

  public static ObjectToSaveInfo newAccountStringInfo(String line){
    JSONObject json  = new JSONObject(line);
    
    ObjectToSaveInfo accountStringInfo = new ObjectToSaveInfo(json);
    return accountStringInfo;
  }

  public String getTags() {
    return field1;
  }

  public String getGeneralLedger() {
    return field2;
  }

  public String getLegalEntity() {
    return field3;
  }

  public String getLocation() {
    return field4;
  }

  public String getCostCenter() {
    return field5;
  }

  public String getTagType() {
    return field6;
  }

  public String getTimestamp() {
    return field7;
  }

  public String getId() {
    return field8;
  }

  public String getProduct() {
    return field9;
  }

  public String getFinancialApprover() {
    return field10;
  }

  public String getName() {
    return field11;
  }

  public String getActive() {
    return field12;
  }

  public String getBusinessApprover() {
    return field13;
  }

  public String getChannel() {
    return field14;
  }

  @Override
  public String toString() {
    return "ObjectToSaveInfo [getTags()=" + getTags() + ", getGeneralLedger()=" + getGeneralLedger()
        + ", getLegalEntity()=" + getLegalEntity() + ", getLocation()=" + getLocation()
        + ", getCostCenter()=" + getCostCenter() + ", getTagType()=" + getTagType()
        + ", getTimestamp()=" + getTimestamp() + ", getId()=" + getId() + ", getProduct()="
        + getProduct() + ", getFinancialApprover()=" + getFinancialApprover() + ", getName()="
        + getName() + ", getActive()=" + getActive() + ", getBusinessApprover()="
        + getBusinessApprover() + ", getChannel()=" + getChannel() + "]";
  }
   
}
