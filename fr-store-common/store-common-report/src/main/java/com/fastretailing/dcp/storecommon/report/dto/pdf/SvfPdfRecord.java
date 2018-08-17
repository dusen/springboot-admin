/**
 * @(#)SvfPdfRecord.java
 * 
 *                       Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.report.dto.pdf;

import java.io.Serializable;
import java.util.List;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This DTO class to handle SVF PDF field record.
 *
 * @author Fast Retailing
 * @version $Revision$
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SvfPdfRecord implements Serializable {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = -6527604011734023182L;

    /**
     * SVF PDF field list.
     */
    @JsonProperty("svf_pdf_field_list")
    @NotNull
    private List<SvfPdfField> svfPdfFieldList;

}
