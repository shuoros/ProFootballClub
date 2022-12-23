/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import CoachComponent from '@/entities/coach/coach.vue';
import CoachClass from '@/entities/coach/coach.component';
import CoachService from '@/entities/coach/coach.service';
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
  describe('Coach Management Component', () => {
    let wrapper: Wrapper<CoachClass>;
    let comp: CoachClass;
    let coachServiceStub: SinonStubbedInstance<CoachService>;

    beforeEach(() => {
      coachServiceStub = sinon.createStubInstance<CoachService>(CoachService);
      coachServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<CoachClass>(CoachComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          coachService: () => coachServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      coachServiceStub.retrieve.resolves({ headers: {}, data: [{ id: '9fec3727-3421-4967-b213-ba36557ca194' }] });

      // WHEN
      comp.retrieveAllCoachs();
      await comp.$nextTick();

      // THEN
      expect(coachServiceStub.retrieve.called).toBeTruthy();
      expect(comp.coaches[0]).toEqual(expect.objectContaining({ id: '9fec3727-3421-4967-b213-ba36557ca194' }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      coachServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: '9fec3727-3421-4967-b213-ba36557ca194' });
      expect(coachServiceStub.retrieve.callCount).toEqual(1);

      comp.removeCoach();
      await comp.$nextTick();

      // THEN
      expect(coachServiceStub.delete.called).toBeTruthy();
      expect(coachServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
