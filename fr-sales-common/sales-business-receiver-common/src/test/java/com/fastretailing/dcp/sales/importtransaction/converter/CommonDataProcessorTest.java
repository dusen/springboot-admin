package com.fastretailing.dcp.sales.importtransaction.converter;

import static org.assertj.core.api.Assertions.assertThat;
import java.time.LocalDateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.fastretailing.dcp.sales.importtransaction.dto.TableCommonItem;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CommonDataProcessorTest {

    @Autowired
    private CommonDataProcessor commonDataProcessor;

    @Test
    public void testGetTableCommonItemForUpdate() {
        String userId = "userId000001";
        TableCommonItem expect = new TableCommonItem();
        expect.setUpdateUserId(userId);
        expect.setUpdateProgramId("SLS0300101");
        expect.setUpdateDatetime(LocalDateTime.now());
        TableCommonItem actual = commonDataProcessor.getTableCommonItemForUpdate(userId);
        assertThat(expect).isEqualToIgnoringGivenFields(actual, "updateDatetime");

    }

    @Test
    public void testGetTableCommonItemForInsert() {
        String userId = "userId000001";
        TableCommonItem expect = new TableCommonItem();
        LocalDateTime dateTime = LocalDateTime.now();

        expect.setCreateUserId(userId);
        expect.setCreateProgramId("SLS0300101");
        expect.setCreateDatetime(dateTime);
        expect.setUpdateUserId(userId);
        expect.setUpdateProgramId("SLS0300101");
        expect.setUpdateDatetime(dateTime);
        TableCommonItem actual = commonDataProcessor.getTableCommonItemForInsert(userId);

        assertThat(expect).isEqualToIgnoringGivenFields(actual, "createDatetime", "updateDatetime");

    }
}
