package com.kanjih.dataflow;



import com.google.api.services.bigquery.model.TableFieldSchema;
import com.google.api.services.bigquery.model.TableRow;
import com.google.api.services.bigquery.model.TableSchema;

import com.kanjih.dataflow.to.ObjectToSaveInfo;

import org.apache.beam.runners.dataflow.options.DataflowPipelineOptions;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.gcp.bigquery.BigQueryIO;
import org.apache.beam.sdk.io.gcp.pubsub.PubsubIO;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.PCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class ConsumeMigration {
  private static final Logger LOG = LoggerFactory.getLogger(ConsumeMigration.class);

  public static interface MyOptions extends DataflowPipelineOptions {


  }

  @SuppressWarnings("serial")
  public static void main(String[] args) {
    MyOptions options = PipelineOptionsFactory.fromArgs(args).withValidation().as(MyOptions.class);
    options.setStreaming(true);

    Pipeline p = Pipeline.create(options);

    String topic = "projects/" + options.getProject() + "/topics/topic_name";
    String bigqueryTable = options.getProject() + ":data_set_name.table_name";

    // Build the table schema for the output table.
    List<TableFieldSchema> fields = new ArrayList<>();
    fields.add(new TableFieldSchema().setName("field1").setType("STRING"));
    fields.add(new TableFieldSchema().setName("fields2").setType("STRING"));
    fields.add(new TableFieldSchema().setName("field3").setType("STRING"));
    fields.add(new TableFieldSchema().setName("field4").setType("STRING"));
    fields.add(new TableFieldSchema().setName("field5").setType("STRING"));
    fields.add(new TableFieldSchema().setName("field6").setType("STRING"));
    fields.add(new TableFieldSchema().setName("field7").setType("STRING"));
    fields.add(new TableFieldSchema().setName("field8").setType("STRING"));
    fields.add(new TableFieldSchema().setName("field9").setType("STRING"));
    fields.add(new TableFieldSchema().setName("field10").setType("STRING"));
    fields.add(new TableFieldSchema().setName("field11").setType("STRING"));
    fields.add(new TableFieldSchema().setName("field12").setType("STRING"));
    fields.add(new TableFieldSchema().setName("field13").setType("STRING"));
    fields.add(new TableFieldSchema().setName("field14").setType("STRING"));
    TableSchema schema = new TableSchema().setFields(fields);



    PCollection<ObjectToSaveInfo> consumeMessage = p //
        .apply("GetMessages", PubsubIO.readStrings().fromTopic(topic)) //
        .apply("ExtractData", ParDo.of(new DoFn<String, ObjectToSaveInfo>() {
          @ProcessElement
          public void processElement(ProcessContext c) throws Exception {
            String line = c.element();
            LOG.info("Receive Line:" + line);
            c.output(ObjectToSaveInfo.newAccountStringInfo(line));
          }
        }));


    consumeMessage.apply("ToBQRow", ParDo.of(new DoFn<ObjectToSaveInfo, TableRow>() {
      @ProcessElement
      public void processElement(ProcessContext c) throws Exception {
        TableRow row = new TableRow();
        ObjectToSaveInfo info = c.element();
        LOG.info("Receive Account String:" + info.toString());
        row.set("field1", info.getTags());
        row.set("field2", info.getGeneralLedger());
        row.set("field3", info.getLegalEntity());
        row.set("field4", info.getLocation());
        row.set("field5", info.getCostCenter());
        row.set("field6", info.getTagType());
        row.set("field7", info.getTimestamp());
        row.set("field8", info.getId());
        row.set("field9", info.getProduct() );
        row.set("field10", info.getFinancialApprover());
        row.set("field11", info.getName());
        row.set("field12", info.getActive());
        row.set("field13", info.getBusinessApprover());
        row.set("field14", info.getChannel());
        c.output(row);
      }
    })) //
        .apply(BigQueryIO.writeTableRows().to(bigqueryTable)//
            .withSchema(schema)//
            .withWriteDisposition(BigQueryIO.Write.WriteDisposition.WRITE_APPEND)
            .withCreateDisposition(BigQueryIO.Write.CreateDisposition.CREATE_IF_NEEDED));
    
    
    p.run();



  }

}
