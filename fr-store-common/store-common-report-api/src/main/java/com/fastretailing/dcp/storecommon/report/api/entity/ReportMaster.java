/**
 * @(#)ReportMaster.java
 * 
 *                       Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.report.api.entity;

import javax.validation.constraints.Size;
import lombok.Data;

/**
 * This class is entity of report master.
 *
 * @author Fast Retailing
 * @version $Revision$
 */
@Data
public class ReportMaster {

    /**
     * Bucket Name.
     */
    @Size(max = 256)
    private String reportFormBucketName;

    /**
     * Key Name.
     */
    @Size(max = 256)
    private String reportFormKeyName;

    /**
     * Title.
     */
    @Size(max = 50)
    private String reportTitle;

    /**
     * Preservation Period.
     */
    private Integer reportPreservationPeriod;

    /**
     * Printer Name.
     */
    @Size(max = 50)
    private String printerName;

    /**
     * Outer Command Execute Flag.
     */
    private Boolean outerCommandExecuteFlag;

    /**
     * Auto Print Server IP Address.
     */
    @Size(max = 15)
    private String autoPrintServerIpAddress;

}
