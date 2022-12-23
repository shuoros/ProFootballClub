<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="stadium">
        <h2 class="jh-entity-heading" data-cy="stadiumDetailsHeading">
          <span v-text="$t('proFootballClubApp.stadium.detail.title')">Stadium</span> {{ stadium.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('proFootballClubApp.stadium.name')">Name</span>
          </dt>
          <dd>
            <span>{{ stadium.name }}</span>
          </dd>
          <dt>
            <span v-text="$t('proFootballClubApp.stadium.capacity')">Capacity</span>
          </dt>
          <dd>
            <span>{{ stadium.capacity }}</span>
          </dd>
          <dt>
            <span v-text="$t('proFootballClubApp.stadium.ticket')">Ticket</span>
          </dt>
          <dd>
            <span>{{ stadium.ticket }}</span>
          </dd>
          <dt>
            <span v-text="$t('proFootballClubApp.stadium.leader')">Leader</span>
          </dt>
          <dd>
            <span v-if="stadium.leader">{{ $d(Date.parse(stadium.leader), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('proFootballClubApp.stadium.vehicle')">Vehicle</span>
          </dt>
          <dd>
            <span v-text="$t('proFootballClubApp.Vehicle.' + stadium.vehicle)">{{ stadium.vehicle }}</span>
          </dd>
          <dt>
            <span v-text="$t('proFootballClubApp.stadium.field')">Field</span>
          </dt>
          <dd>
            <span v-text="$t('proFootballClubApp.Field.' + stadium.field)">{{ stadium.field }}</span>
          </dd>
          <dt>
            <span v-text="$t('proFootballClubApp.stadium.light')">Light</span>
          </dt>
          <dd>
            <span v-text="$t('proFootballClubApp.Light.' + stadium.light)">{{ stadium.light }}</span>
          </dd>
          <dt>
            <span v-text="$t('proFootballClubApp.stadium.chair')">Chair</span>
          </dt>
          <dd>
            <span v-text="$t('proFootballClubApp.Chair.' + stadium.chair)">{{ stadium.chair }}</span>
          </dd>
          <dt>
            <span v-text="$t('proFootballClubApp.stadium.assistant')">Assistant</span>
          </dt>
          <dd>
            <span v-text="$t('proFootballClubApp.Assistant.' + stadium.assistant)">{{ stadium.assistant }}</span>
          </dd>
          <dt>
            <span v-text="$t('proFootballClubApp.stadium.bodyBuilding')">Body Building</span>
          </dt>
          <dd>
            <span v-text="$t('proFootballClubApp.BodyBuilding.' + stadium.bodyBuilding)">{{ stadium.bodyBuilding }}</span>
          </dd>
          <dt>
            <span v-text="$t('proFootballClubApp.stadium.doctor')">Doctor</span>
          </dt>
          <dd>
            <span v-text="$t('proFootballClubApp.Doctor.' + stadium.doctor)">{{ stadium.doctor }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="stadium.id" :to="{ name: 'StadiumEdit', params: { stadiumId: stadium.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./stadium-details.component.ts"></script>
