/**
 * 
 */
package ru.naumen.pentago.game.positionchecker;

import java.util.ArrayList;
import java.util.List;

import ru.naumen.pentago.game.model.Board;
import ru.naumen.pentago.game.positionchecker.iterators.LineIteratorFactory;

/**
 * @author ivodopyanov
 * @since 09.10.2012
 * 
 */
public class PositionScannerMultiPredicate extends PositionScanner
{
    protected final List<LineCheckPredicate> predicates;

    public PositionScannerMultiPredicate(CheckPatternSet[] patternSets, LineIteratorFactory[] lineIteratorFactories,
            Board board)
    {
        super(lineIteratorFactories, board);
        predicates = new ArrayList<LineCheckPredicate>();
        for (CheckPatternSet patternSet : patternSets)
        {
            predicates.add(new LineCheckPredicate(patternSet));
        }
    }
}