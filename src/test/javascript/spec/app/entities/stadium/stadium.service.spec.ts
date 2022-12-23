/* tslint:disable max-line-length */
import axios from 'axios';
import sinon from 'sinon';
import dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from '@/shared/date/filters';
import StadiumService from '@/entities/stadium/stadium.service';
import { Stadium } from '@/shared/model/stadium.model';
import { Vehicle } from '@/shared/model/enumerations/vehicle.model';
import { Field } from '@/shared/model/enumerations/field.model';
import { Light } from '@/shared/model/enumerations/light.model';
import { Chair } from '@/shared/model/enumerations/chair.model';
import { Assistant } from '@/shared/model/enumerations/assistant.model';
import { BodyBuilding } from '@/shared/model/enumerations/body-building.model';
import { Doctor } from '@/shared/model/enumerations/doctor.model';

const error = {
  response: {
    status: null,
    data: {
      type: null,
    },
  },
};

const axiosStub = {
  get: sinon.stub(axios, 'get'),
  post: sinon.stub(axios, 'post'),
  put: sinon.stub(axios, 'put'),
  patch: sinon.stub(axios, 'patch'),
  delete: sinon.stub(axios, 'delete'),
};

describe('Service Tests', () => {
  describe('Stadium Service', () => {
    let service: StadiumService;
    let elemDefault;
    let currentDate: Date;

    beforeEach(() => {
      service = new StadiumService();
      currentDate = new Date();
      elemDefault = new Stadium(
        '9fec3727-3421-4967-b213-ba36557ca194',
        'AAAAAAA',
        0,
        0,
        currentDate,
        Vehicle.BUS,
        Field.CLAY,
        Light.NO_LIGHT,
        Chair.WOODEN,
        Assistant.NO_ASSISTANT,
        BodyBuilding.NO_BODYBUILDING,
        Doctor.NO_DOCTOR
      );
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            leader: dayjs(currentDate).format(DATE_TIME_FORMAT),
          },
          elemDefault
        );
        axiosStub.get.resolves({ data: returnedFromService });

        return service.find('9fec3727-3421-4967-b213-ba36557ca194').then(res => {
          expect(res).toMatchObject(elemDefault);
        });
      });

      it('should not find an element', async () => {
        axiosStub.get.rejects(error);
        return service
          .find('9fec3727-3421-4967-b213-ba36557ca194')
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should create a Stadium', async () => {
        const returnedFromService = Object.assign(
          {
            id: '9fec3727-3421-4967-b213-ba36557ca194',
            leader: dayjs(currentDate).format(DATE_TIME_FORMAT),
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            leader: currentDate,
          },
          returnedFromService
        );

        axiosStub.post.resolves({ data: returnedFromService });
        return service.create({}).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not create a Stadium', async () => {
        axiosStub.post.rejects(error);

        return service
          .create({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should update a Stadium', async () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            capacity: 1,
            ticket: 1,
            leader: dayjs(currentDate).format(DATE_TIME_FORMAT),
            vehicle: 'BBBBBB',
            field: 'BBBBBB',
            light: 'BBBBBB',
            chair: 'BBBBBB',
            assistant: 'BBBBBB',
            bodyBuilding: 'BBBBBB',
            doctor: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            leader: currentDate,
          },
          returnedFromService
        );
        axiosStub.put.resolves({ data: returnedFromService });

        return service.update(expected).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not update a Stadium', async () => {
        axiosStub.put.rejects(error);

        return service
          .update({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should partial update a Stadium', async () => {
        const patchObject = Object.assign(
          {
            name: 'BBBBBB',
            ticket: 1,
            bodyBuilding: 'BBBBBB',
          },
          new Stadium()
        );
        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            leader: currentDate,
          },
          returnedFromService
        );
        axiosStub.patch.resolves({ data: returnedFromService });

        return service.partialUpdate(patchObject).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not partial update a Stadium', async () => {
        axiosStub.patch.rejects(error);

        return service
          .partialUpdate({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should return a list of Stadium', async () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            capacity: 1,
            ticket: 1,
            leader: dayjs(currentDate).format(DATE_TIME_FORMAT),
            vehicle: 'BBBBBB',
            field: 'BBBBBB',
            light: 'BBBBBB',
            chair: 'BBBBBB',
            assistant: 'BBBBBB',
            bodyBuilding: 'BBBBBB',
            doctor: 'BBBBBB',
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            leader: currentDate,
          },
          returnedFromService
        );
        axiosStub.get.resolves([returnedFromService]);
        return service.retrieve().then(res => {
          expect(res).toContainEqual(expected);
        });
      });

      it('should not return a list of Stadium', async () => {
        axiosStub.get.rejects(error);

        return service
          .retrieve()
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should delete a Stadium', async () => {
        axiosStub.delete.resolves({ ok: true });
        return service.delete('9fec3727-3421-4967-b213-ba36557ca194').then(res => {
          expect(res.ok).toBeTruthy();
        });
      });

      it('should not delete a Stadium', async () => {
        axiosStub.delete.rejects(error);

        return service
          .delete('9fec3727-3421-4967-b213-ba36557ca194')
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });
    });
  });
});
