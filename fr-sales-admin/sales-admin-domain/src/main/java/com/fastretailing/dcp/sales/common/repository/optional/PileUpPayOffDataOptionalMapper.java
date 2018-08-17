/**
 * @(#)PileUpPayOffDataOptionalMapper.java
 *
 *                                         Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.repository.optional;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.fastretailing.dcp.sales.common.entity.optional.PileUpPayOffDataOptional;

@Mapper
public interface PileUpPayOffDataOptionalMapper {

    /**
     * Select integrity check pile up pay off data optional.
     *
     * @param condition Pile up pay off dataOptional.
     * @return Integrity check pile up pay off data optional.
     */
    List<PileUpPayOffDataOptional> selectIntegrityCheckPileUpPayOffDataOptional(
            PileUpPayOffDataOptional condition);
    
    /**
     * Select declare irregular settlements pile up pay off data optional.
     *
     * @param condition Pile up pay off dataOptional.
     * @return Pile up pay off data optional.
     */
    List<PileUpPayOffDataOptional> selectDeclareIrregularSettlementsPileUpPayOffDataOptional(
            PileUpPayOffDataOptional condition);
}
