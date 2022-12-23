<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="proFootballClubApp.stadium.home.createOrEditLabel"
          data-cy="StadiumCreateUpdateHeading"
          v-text="$t('proFootballClubApp.stadium.home.createOrEditLabel')"
        >
          Create or edit a Stadium
        </h2>
        <div>
          <div class="form-group" v-if="stadium.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="stadium.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.stadium.name')" for="stadium-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="stadium-name"
              data-cy="name"
              :class="{ valid: !$v.stadium.name.$invalid, invalid: $v.stadium.name.$invalid }"
              v-model="$v.stadium.name.$model"
              required
            />
            <div v-if="$v.stadium.name.$anyDirty && $v.stadium.name.$invalid">
              <small class="form-text text-danger" v-if="!$v.stadium.name.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.stadium.capacity')" for="stadium-capacity">Capacity</label>
            <input
              type="number"
              class="form-control"
              name="capacity"
              id="stadium-capacity"
              data-cy="capacity"
              :class="{ valid: !$v.stadium.capacity.$invalid, invalid: $v.stadium.capacity.$invalid }"
              v-model.number="$v.stadium.capacity.$model"
              required
            />
            <div v-if="$v.stadium.capacity.$anyDirty && $v.stadium.capacity.$invalid">
              <small class="form-text text-danger" v-if="!$v.stadium.capacity.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.stadium.capacity.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.stadium.ticket')" for="stadium-ticket">Ticket</label>
            <input
              type="number"
              class="form-control"
              name="ticket"
              id="stadium-ticket"
              data-cy="ticket"
              :class="{ valid: !$v.stadium.ticket.$invalid, invalid: $v.stadium.ticket.$invalid }"
              v-model.number="$v.stadium.ticket.$model"
              required
            />
            <div v-if="$v.stadium.ticket.$anyDirty && $v.stadium.ticket.$invalid">
              <small class="form-text text-danger" v-if="!$v.stadium.ticket.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.stadium.ticket.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.stadium.leader')" for="stadium-leader">Leader</label>
            <div class="d-flex">
              <input
                id="stadium-leader"
                data-cy="leader"
                type="datetime-local"
                class="form-control"
                name="leader"
                :class="{ valid: !$v.stadium.leader.$invalid, invalid: $v.stadium.leader.$invalid }"
                :value="convertDateTimeFromServer($v.stadium.leader.$model)"
                @change="updateInstantField('leader', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.stadium.vehicle')" for="stadium-vehicle">Vehicle</label>
            <select
              class="form-control"
              name="vehicle"
              :class="{ valid: !$v.stadium.vehicle.$invalid, invalid: $v.stadium.vehicle.$invalid }"
              v-model="$v.stadium.vehicle.$model"
              id="stadium-vehicle"
              data-cy="vehicle"
            >
              <option
                v-for="vehicle in vehicleValues"
                :key="vehicle"
                v-bind:value="vehicle"
                v-bind:label="$t('proFootballClubApp.Vehicle.' + vehicle)"
              >
                {{ vehicle }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.stadium.field')" for="stadium-field">Field</label>
            <select
              class="form-control"
              name="field"
              :class="{ valid: !$v.stadium.field.$invalid, invalid: $v.stadium.field.$invalid }"
              v-model="$v.stadium.field.$model"
              id="stadium-field"
              data-cy="field"
            >
              <option v-for="field in fieldValues" :key="field" v-bind:value="field" v-bind:label="$t('proFootballClubApp.Field.' + field)">
                {{ field }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.stadium.light')" for="stadium-light">Light</label>
            <select
              class="form-control"
              name="light"
              :class="{ valid: !$v.stadium.light.$invalid, invalid: $v.stadium.light.$invalid }"
              v-model="$v.stadium.light.$model"
              id="stadium-light"
              data-cy="light"
            >
              <option v-for="light in lightValues" :key="light" v-bind:value="light" v-bind:label="$t('proFootballClubApp.Light.' + light)">
                {{ light }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.stadium.chair')" for="stadium-chair">Chair</label>
            <select
              class="form-control"
              name="chair"
              :class="{ valid: !$v.stadium.chair.$invalid, invalid: $v.stadium.chair.$invalid }"
              v-model="$v.stadium.chair.$model"
              id="stadium-chair"
              data-cy="chair"
            >
              <option v-for="chair in chairValues" :key="chair" v-bind:value="chair" v-bind:label="$t('proFootballClubApp.Chair.' + chair)">
                {{ chair }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.stadium.assistant')" for="stadium-assistant">Assistant</label>
            <select
              class="form-control"
              name="assistant"
              :class="{ valid: !$v.stadium.assistant.$invalid, invalid: $v.stadium.assistant.$invalid }"
              v-model="$v.stadium.assistant.$model"
              id="stadium-assistant"
              data-cy="assistant"
            >
              <option
                v-for="assistant in assistantValues"
                :key="assistant"
                v-bind:value="assistant"
                v-bind:label="$t('proFootballClubApp.Assistant.' + assistant)"
              >
                {{ assistant }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.stadium.bodyBuilding')" for="stadium-bodyBuilding"
              >Body Building</label
            >
            <select
              class="form-control"
              name="bodyBuilding"
              :class="{ valid: !$v.stadium.bodyBuilding.$invalid, invalid: $v.stadium.bodyBuilding.$invalid }"
              v-model="$v.stadium.bodyBuilding.$model"
              id="stadium-bodyBuilding"
              data-cy="bodyBuilding"
            >
              <option
                v-for="bodyBuilding in bodyBuildingValues"
                :key="bodyBuilding"
                v-bind:value="bodyBuilding"
                v-bind:label="$t('proFootballClubApp.BodyBuilding.' + bodyBuilding)"
              >
                {{ bodyBuilding }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.stadium.doctor')" for="stadium-doctor">Doctor</label>
            <select
              class="form-control"
              name="doctor"
              :class="{ valid: !$v.stadium.doctor.$invalid, invalid: $v.stadium.doctor.$invalid }"
              v-model="$v.stadium.doctor.$model"
              id="stadium-doctor"
              data-cy="doctor"
            >
              <option
                v-for="doctor in doctorValues"
                :key="doctor"
                v-bind:value="doctor"
                v-bind:label="$t('proFootballClubApp.Doctor.' + doctor)"
              >
                {{ doctor }}
              </option>
            </select>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.stadium.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./stadium-update.component.ts"></script>
