/**
 * @(#)SvfPdfSection.java
 * 
 *                        Copyright (c) 2018 Fast Retailing Corporation.
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
 * This DTO class to handle SVF PDF section.
 *
 * @author Fast Retailing
 * @version $Revision$
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SvfPdfSection implements Serializable {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = -2523266423167052054L;

    /**
     * SVF PDF record list.
     */
    @JsonProperty("svf_pdf_record_list")
    @NotNull
    private List<SvfPdfRecord> svfPdfRecordList;

}
