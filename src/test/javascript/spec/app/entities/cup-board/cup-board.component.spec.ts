/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import CupBoardComponent from '@/entities/cup-board/cup-board.vue';
import CupBoardClass from '@/entities/cup-board/cup-board.component';
import CupBoardService from '@/entities/cup-board/cup-board.service';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(ToastPlugin);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('b-badge', {});
localVue.directive('b-modal', {});
localVue.component('b-button', {});
localVue.component('router-link', {});

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  describe('CupBoard Management Component', () => {
    let wrapper: Wrapper<CupBoardClass>;
    let comp: CupBoardClass;
    let cupBoardServiceStub: SinonStubbedInstance<CupBoardService>;

    beforeEach(() => {
      cupBoardServiceStub = sinon.createStubInstance<CupBoardService>(CupBoardService);
      cupBoardServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<CupBoardClass>(CupBoardComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          cupBoardService: () => cupBoardServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      cupBoardServiceStub.retrieve.resolves({ headers: {}, data: [{ id: '9fec3727-3421-4967-b213-ba36557ca194' }] });

      // WHEN
      comp.retrieveAllCupBoards();
      await comp.$nextTick();

      // THEN
      expect(cupBoardServiceStub.retrieve.called).toBeTruthy();
      expect(comp.cupBoards[0]).toEqual(expect.objectContaining({ id: '9fec3727-3421-4967-b213-ba36557ca194' }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      cupBoardServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: '9fec3727-3421-4967-b213-ba36557ca194' });
      expect(cupBoardServiceStub.retrieve.callCount).toEqual(1);

      comp.removeCupBoard();
      await comp.$nextTick();

      // THEN
      expect(cupBoardServiceStub.delete.called).toBeTruthy();
      expect(cupBoardServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
