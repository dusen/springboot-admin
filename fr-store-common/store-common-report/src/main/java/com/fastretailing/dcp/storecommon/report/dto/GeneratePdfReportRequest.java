/**
 * @(#)GeneratePdfReportRequest.java
 * 
 *                                   Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.report.dto;

import java.io.Serializable;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fastretailing.dcp.storecommon.report.dto.pdf.SvfPdfSection;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Implement PDF report Order Request class to generate.
 *
 * @author Fast Retailing
 * @version $Revision$
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class GeneratePdfReportRequest extends GenerateReportRequestBase implements Serializable {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 4599387421119756187L;

    /**
     * Asynchronously Setting.
     */
    @JsonProperty("async_setting")
    @NotNull
    private Boolean asyncSetting;

    /**
     * SVF PDF section list.
     */
    @JsonProperty("svf_pdf_section_list")
    @NotEmpty
    private List<SvfPdfSection> svfPdfSectionList;
}
