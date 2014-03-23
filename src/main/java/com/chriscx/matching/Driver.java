/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.chriscx.matching;

import com.chriscx.mapred.Map;
import com.chriscx.mapred.Reduce;
import java.util.ArrayList;
import java.util.List;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.TextInputFormat;
import org.apache.hadoop.mapred.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

/**
 *
 * @author Chris
 */
public class Driver  extends Configured implements Tool {
   public int run(String[] args) throws Exception {
     JobConf conf = new JobConf(getConf(), com.chriscx.mapred.Driver.class);
     conf.setJobName("Matching");

     conf.setOutputKeyClass(Text.class);
     conf.setOutputValueClass(IntWritable.class);

     conf.setMapperClass(Map.class);
     conf.setCombinerClass(Reduce.class);
     conf.setReducerClass(Reduce.class);
     
     conf.setInputFormat(TextInputFormat.class);
     conf.setOutputFormat(TextOutputFormat.class);

     List<String> other_args = new ArrayList<String>();
     for (int i=0; i < args.length; ++i) {
       if ("-skip".equals(args[i])) {
         DistributedCache.addCacheFile(new Path(args[++i]).toUri(), conf);
         conf.setBoolean("wordcount.skip.patterns", true);
       } else {
         other_args.add(args[i]);
       }
     }

     FileInputFormat.setInputPaths(conf, new Path(other_args.get(0)));
     FileOutputFormat.setOutputPath(conf, new Path(other_args.get(1)));

     JobClient.runJob(conf);
     return 0;
   }

   public static void main(String[] args) throws Exception {
     int res = ToolRunner.run(new Configuration(), new com.chriscx.mapred.Driver(), args);
     System.exit(res);
   }
}
