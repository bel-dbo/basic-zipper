package batch;

import java.util.List;

import org.springframework.batch.item.file.FlatFileItemWriter;

import zipper.domain.Stuff;

public class MyFlatFileItemWriter extends FlatFileItemWriter<Stuff> {

	@Override
	public void write(List<? extends Stuff> arg0) throws Exception {
		// TODO Auto-generated method stub
		super.write(arg0);
	}

	@Override
	public String getExecutionContextKey(String key) {
		// TODO Auto-generated method stub
		return super.getExecutionContextKey(key);
	}

	@Override
	protected void setExecutionContextName(String name) {
		// TODO Auto-generated method stub
		super.setExecutionContextName(name);
	}

}
