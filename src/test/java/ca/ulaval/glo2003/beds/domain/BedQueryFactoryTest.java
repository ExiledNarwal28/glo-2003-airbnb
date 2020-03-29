package ca.ulaval.glo2003.beds.domain;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import ca.ulaval.glo2003.beds.domain.assemblers.BedQueryParamAssembler;
import ca.ulaval.glo2003.beds.domain.assemblers.BedTypeQueryParamAssembler;
import ca.ulaval.glo2003.beds.infrastructure.InMemoryBedQuery;
import java.util.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BedQueryFactoryTest {

  private static BedQueryFactory bedQueryFactory;
  private static BedQueryBuilder bedQueryBuilder = mock(BedQueryBuilder.class);
  private static BedQueryBuilder filteredBedQueryBuilder = mock(BedQueryBuilder.class);

  private BedTypeQueryParamAssembler firstQueryAssembler = mock(BedTypeQueryParamAssembler.class);
  private InMemoryBedQuery query = mock(InMemoryBedQuery.class);
  private InMemoryBedQuery filteredQuery = mock(InMemoryBedQuery.class);
  private Map<String, String[]> params = new HashMap<>();

  private Set<BedQueryParamAssembler> queryParamAssemblers =
      new HashSet<>(Collections.singletonList(firstQueryAssembler));

  @BeforeEach
  public void setUpMocks() {
    when(bedQueryBuilder.aBedQuery()).thenReturn(bedQueryBuilder);
    when(bedQueryBuilder.build()).thenReturn(query);
    when(filteredBedQueryBuilder.build()).thenReturn(filteredQuery);
    when(firstQueryAssembler.assemble(bedQueryBuilder, params)).thenReturn(filteredBedQueryBuilder);
  }

  @Test
  public void create_withoutAssembler_shouldCreateQuery() {
    bedQueryFactory = new BedQueryFactory(bedQueryBuilder, Collections.emptySet());

    InMemoryBedQuery actualQuery = bedQueryFactory.create(params);

    assertSame(query, actualQuery);
  }

  @Test
  public void create_withAssemblers_shouldCreateQuery() {
    bedQueryFactory = new BedQueryFactory(bedQueryBuilder, queryParamAssemblers);

    InMemoryBedQuery actualQuery = bedQueryFactory.create(params);

    assertSame(filteredQuery, actualQuery);
  }
}