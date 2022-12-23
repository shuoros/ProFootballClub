<template>
  <div>
    <h2 id="page-heading" data-cy="StadiumHeading">
      <span v-text="$t('proFootballClubApp.stadium.home.title')" id="stadium-heading">Stadiums</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('proFootballClubApp.stadium.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'StadiumCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-stadium"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('proFootballClubApp.stadium.home.createLabel')"> Create a new Stadium </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && stadiums && stadiums.length === 0">
      <span v-text="$t('proFootballClubApp.stadium.home.notFound')">No stadiums found</span>
    </div>
    <div class="table-responsive" v-if="stadiums && stadiums.length > 0">
      <table class="table table-striped" aria-describedby="stadiums">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('proFootballClubApp.stadium.name')">Name</span></th>
            <th scope="row"><span v-text="$t('proFootballClubApp.stadium.capacity')">Capacity</span></th>
            <th scope="row"><span v-text="$t('proFootballClubApp.stadium.ticket')">Ticket</span></th>
            <th scope="row"><span v-text="$t('proFootballClubApp.stadium.leader')">Leader</span></th>
            <th scope="row"><span v-text="$t('proFootballClubApp.stadium.vehicle')">Vehicle</span></th>
            <th scope="row"><span v-text="$t('proFootballClubApp.stadium.field')">Field</span></th>
            <th scope="row"><span v-text="$t('proFootballClubApp.stadium.light')">Light</span></th>
            <th scope="row"><span v-text="$t('proFootballClubApp.stadium.chair')">Chair</span></th>
            <th scope="row"><span v-text="$t('proFootballClubApp.stadium.assistant')">Assistant</span></th>
            <th scope="row"><span v-text="$t('proFootballClubApp.stadium.bodyBuilding')">Body Building</span></th>
            <th scope="row"><span v-text="$t('proFootballClubApp.stadium.doctor')">Doctor</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="stadium in stadiums" :key="stadium.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'StadiumView', params: { stadiumId: stadium.id } }">{{ stadium.id }}</router-link>
            </td>
            <td>{{ stadium.name }}</td>
            <td>{{ stadium.capacity }}</td>
            <td>{{ stadium.ticket }}</td>
            <td>{{ stadium.leader ? $d(Date.parse(stadium.leader), 'short') : '' }}</td>
            <td v-text="$t('proFootballClubApp.Vehicle.' + stadium.vehicle)">{{ stadium.vehicle }}</td>
            <td v-text="$t('proFootballClubApp.Field.' + stadium.field)">{{ stadium.field }}</td>
            <td v-text="$t('proFootballClubApp.Light.' + stadium.light)">{{ stadium.light }}</td>
            <td v-text="$t('proFootballClubApp.Chair.' + stadium.chair)">{{ stadium.chair }}</td>
            <td v-text="$t('proFootballClubApp.Assistant.' + stadium.assistant)">{{ stadium.assistant }}</td>
            <td v-text="$t('proFootballClubApp.BodyBuilding.' + stadium.bodyBuilding)">{{ stadium.bodyBuilding }}</td>
            <td v-text="$t('proFootballClubApp.Doctor.' + stadium.doctor)">{{ stadium.doctor }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'StadiumView', params: { stadiumId: stadium.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'StadiumEdit', params: { stadiumId: stadium.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(stadium)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="$t('entity.action.delete')">Delete</span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <span slot="modal-title"
        ><span id="proFootballClubApp.stadium.delete.question" data-cy="stadiumDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-stadium-heading" v-text="$t('proFootballClubApp.stadium.delete.question', { id: removeId })">
          Are you sure you want to delete this Stadium?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-stadium"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeStadium()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./stadium.component.ts"></script>
